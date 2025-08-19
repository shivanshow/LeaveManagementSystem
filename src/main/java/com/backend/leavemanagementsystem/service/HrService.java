package com.backend.leavemanagementsystem.service;

import com.backend.leavemanagementsystem.dto.*;
import com.backend.leavemanagementsystem.entity.Employee;
import com.backend.leavemanagementsystem.entity.LeaveBalance;
import com.backend.leavemanagementsystem.entity.LeaveRequest;
import com.backend.leavemanagementsystem.exceptions.*;
import com.backend.leavemanagementsystem.repository.EmployeeRepository;
import com.backend.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.backend.leavemanagementsystem.repository.LeaveRequestRepository;
import com.backend.leavemanagementsystem.utils.LeaveStatus;
import com.backend.leavemanagementsystem.utils.LeaveType;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HrService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public HrService(
            EmployeeRepository employeeRepository,
            LeaveRequestRepository leaveRequestRepository,
            LeaveBalanceRepository leaveBalanceRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    @Transactional
    public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto) {
        if (employeeRepository.existsByEmail(employeeRequestDto.email())) {
            throw new EmployeeExistsException("Employee already exists with email: " + employeeRequestDto.email());
        }
        Employee employee = Employee.builder()
                .name(employeeRequestDto.name())
                .email(employeeRequestDto.email())
                .department(employeeRequestDto.department())
                .joiningDate(employeeRequestDto.joiningDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        LeaveBalance balance = LeaveBalance.builder()
                .employee(employee)
                .vacationDays(20)
                .sickDays(10)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        employee.setLeaveBalance(balance);

        Employee saved = employeeRepository.save(employee);

        return EmployeeResponseDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .department(saved.getDepartment())
                .joiningDate(saved.getJoiningDate().toString())
                .vacationDays(saved.getLeaveBalance().getVacationDays())
                .sickDays(saved.getLeaveBalance().getSickDays())
                .build();
    }

    @Transactional
    public LeaveRequestResponseDto applyLeave(LeaveRequestDto dto) {
        Employee employee = employeeRepository
                .findById(dto.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Validate dates
        if (dto.endDate().isBefore(dto.startDate())) {
            throw new InvalidDateException("End date cannot be before start date");
        }
        if (dto.startDate().isBefore(employee.getJoiningDate())) {
            throw new LeaveBeforeJoiningException("Cannot apply leave before joining date");
        }

        // Check overlapping leave
        boolean overlapping = leaveRequestRepository.existsByEmployeeAndStatusInAndDateRange(
                employee, List.of(LeaveStatus.PENDING, LeaveStatus.APPROVED), dto.startDate(), dto.endDate());
        if (overlapping) {
            throw new OverlappingLeaveException("Overlapping leave request exists");
        }

        // Check leave balance
        LeaveBalance balance = employee.getLeaveBalance();
        long daysRequested = ChronoUnit.DAYS.between(dto.startDate(), dto.endDate()) + 1;

        switch (dto.leaveType()) {
            case VACATION -> {
                if (balance.getVacationDays() < daysRequested)
                    throw new InsufficientLeaveBalanceException("Not enough vacation days");
                balance.setVacationDays(balance.getVacationDays() - (int) daysRequested);
            }
            case SICK -> {
                if (balance.getSickDays() < daysRequested)
                    throw new InsufficientLeaveBalanceException("Not enough sick days");
                balance.setSickDays(balance.getSickDays() - (int) daysRequested);
            }
            case UNPAID -> {} // no limit
        }

        // 4️⃣ Save leave request
        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .leaveType(dto.leaveType())
                .status(LeaveStatus.PENDING)
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .reason(dto.reason())
                .build();

        leaveRequestRepository.save(leaveRequest);

        return new LeaveRequestResponseDto(
                leaveRequest.getId(),
                employee.getId(),
                leaveRequest.getLeaveType(),
                leaveRequest.getStatus(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason());
    }

    @Transactional
    public LeaveRequestResponseDto updateLeaveStatus(LeaveStatusUpdateRequest request) {

        var leaveRequest = leaveRequestRepository
                .findById(request.leaveRequestId())
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        switch (request.action().toUpperCase()) {
            case "APPROVE":
                leaveRequest.setStatus(LeaveStatus.APPROVED);
                // Deduct balance if VACATION or SICK
                LeaveBalance balance = leaveRequest.getEmployee().getLeaveBalance();
                long days = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;

                if (leaveRequest.getLeaveType() == LeaveType.VACATION) {
                    if (balance.getVacationDays() < days) {
                        throw new IllegalArgumentException("Not enough vacation days.");
                    }
                    balance.setVacationDays(balance.getVacationDays() - (int) days);
                } else if (leaveRequest.getLeaveType() == LeaveType.SICK) {
                    if (balance.getSickDays() < days) {
                        throw new IllegalArgumentException("Not enough sick days.");
                    }
                    balance.setSickDays(balance.getSickDays() - (int) days);
                }
                leaveBalanceRepository.save(balance);
                break;

            case "REJECT":
                leaveRequest.setStatus(LeaveStatus.REJECTED);
                break;

            default:
                throw new IllegalArgumentException("Invalid action: " + request.action());
        }

        leaveRequestRepository.save(leaveRequest);

        return new LeaveRequestResponseDto(
                leaveRequest.getId(),
                leaveRequest.getEmployee().getId(),
                leaveRequest.getLeaveType(),
                leaveRequest.getStatus(),
                leaveRequest.getEndDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason());
    }

    public LeaveBalanceResponseDto getLeaveBalance(Long employeeId) {
        var balance = leaveBalanceRepository
                .findByEmployeeId(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found or no balance"));

        return LeaveBalanceResponseDto.builder()
                .employeeId(balance.getEmployee().getId())
                .vacationDays(balance.getVacationDays())
                .sickDays(balance.getSickDays())
                .build();
    }
}

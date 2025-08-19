package com.backend.leavemanagementsystem.dto;

import com.backend.leavemanagementsystem.utils.LeaveStatus;
import com.backend.leavemanagementsystem.utils.LeaveType;
import java.time.LocalDate;

public record LeaveRequestResponseDto(
        Long id,
        Long employeeId,
        LeaveType leaveType,
        LeaveStatus status,
        LocalDate startDate,
        LocalDate endDate,
        String reason) {}

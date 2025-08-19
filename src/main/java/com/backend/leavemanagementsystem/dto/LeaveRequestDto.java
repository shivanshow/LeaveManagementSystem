package com.backend.leavemanagementsystem.dto;

import com.backend.leavemanagementsystem.utils.LeaveType;
import java.time.LocalDate;

public record LeaveRequestDto(
        Long employeeId, LeaveType leaveType, LocalDate startDate, LocalDate endDate, String reason) {}

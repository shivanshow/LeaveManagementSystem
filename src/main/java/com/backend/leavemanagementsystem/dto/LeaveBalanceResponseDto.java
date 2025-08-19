package com.backend.leavemanagementsystem.dto;

import lombok.Builder;

@Builder
public record LeaveBalanceResponseDto(Long employeeId, int vacationDays, int sickDays) {}

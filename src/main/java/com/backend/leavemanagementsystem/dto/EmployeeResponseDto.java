package com.backend.leavemanagementsystem.dto;

import lombok.Builder;

@Builder
public record EmployeeResponseDto(
        Long id, String name, String email, String department, String joiningDate, int vacationDays, int sickDays) {}

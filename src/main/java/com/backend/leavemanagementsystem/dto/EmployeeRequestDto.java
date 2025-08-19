package com.backend.leavemanagementsystem.dto;

import java.time.LocalDate;

public record EmployeeRequestDto(String name, String email, String department, LocalDate joiningDate) {}

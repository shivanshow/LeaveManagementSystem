package com.backend.leavemanagementsystem.dto;

public record LeaveStatusUpdateRequest(Long leaveRequestId, String action // "APPROVE" or "REJECT"
        ) {}

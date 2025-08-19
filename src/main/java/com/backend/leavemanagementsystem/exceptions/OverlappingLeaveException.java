package com.backend.leavemanagementsystem.exceptions;

public class OverlappingLeaveException extends RuntimeException {
    public OverlappingLeaveException(String message) {
        super(message);
    }
}

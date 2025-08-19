package com.backend.leavemanagementsystem.exceptions;

public class LeaveBeforeJoiningException extends RuntimeException {
    public LeaveBeforeJoiningException(String message) {
        super(message);
    }
}

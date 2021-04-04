package com.learning.studentservice.exception;

public class StudentNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE = "default exception message not found ";

    public StudentNotFoundException() {
        super(NOT_FOUND_MESSAGE);
    }

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable exception) {
        super(message, exception);
    }
}

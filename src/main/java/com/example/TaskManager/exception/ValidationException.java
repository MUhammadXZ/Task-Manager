package com.example.TaskManager.exception;

import com.example.TaskManager.exception.base.ApiException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class ValidationException extends ApiException {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("Validation failed", HttpStatus.BAD_REQUEST);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
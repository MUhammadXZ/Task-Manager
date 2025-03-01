package com.example.TaskManager.exception;

import com.example.TaskManager.exception.base.ApiException;
import org.springframework.http.HttpStatus;

public class TokenValidationException extends ApiException {
    public TokenValidationException(String message) {
        super("Token validation failed: " + message, HttpStatus.UNAUTHORIZED);
    }
}

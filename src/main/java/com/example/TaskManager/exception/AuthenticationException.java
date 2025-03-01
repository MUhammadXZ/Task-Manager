package com.example.TaskManager.exception;

import com.example.TaskManager.exception.base.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;

public class AuthenticationException extends ApiException {
    public AuthenticationException(String message, DisabledException ex) {
        super("Authentication failed: " + message, HttpStatus.UNAUTHORIZED);
    }
}

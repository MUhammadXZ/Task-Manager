package com.example.TaskManager.exception;

import com.example.TaskManager.exception.base.ApiException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String resource) {
        super(resource + " not found", HttpStatus.NOT_FOUND);
    }
}
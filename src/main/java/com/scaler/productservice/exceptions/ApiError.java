package com.scaler.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ApiError {

    private int status;
    private String error;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;

    public ApiError(int status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
    public ApiError(int status, String error, String path, Map<String, String> validationErrors) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.validationErrors = validationErrors;
        this.timestamp = LocalDateTime.now();
    }
}

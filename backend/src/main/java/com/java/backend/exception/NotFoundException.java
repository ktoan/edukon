package com.java.backend.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String _message) {
        super(_message);
        this.message = _message;
    }
}

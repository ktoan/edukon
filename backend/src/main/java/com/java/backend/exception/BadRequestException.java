package com.java.backend.exception;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException(String _message) {
        super(_message);
        this.message = _message;
    }
}

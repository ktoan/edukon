package com.java.backend.exception;

import lombok.Data;

@Data
public class NotAccessException extends RuntimeException {
    private String message;

    public NotAccessException(String _message) {
        super(_message);
        this.message = _message;
    }
}

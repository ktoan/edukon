package com.java.backend.exception;

import lombok.Getter;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Getter
public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException(String _message) {
        super(_message);
        this.message = _message;
    }
}

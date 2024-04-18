package com.java.backend.exception;

import lombok.Getter;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Getter
public class NotAccessException extends RuntimeException {
    private String message;

    public NotAccessException(String _message) {
        super(_message);
        this.message = _message;
    }
}

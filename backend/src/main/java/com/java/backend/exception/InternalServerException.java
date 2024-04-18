package com.java.backend.exception;

import lombok.Getter;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Getter
public class InternalServerException extends RuntimeException {
    private String message;

    public InternalServerException(String _message) {
        super(_message);
        this.message = _message;
    }
}

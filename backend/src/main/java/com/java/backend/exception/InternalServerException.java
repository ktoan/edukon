package com.java.backend.exception;

import lombok.Data;

@Data
public class InternalServerException extends RuntimeException {
	private String message;

	public InternalServerException(String _message) {
		super(_message);
		this.message = _message;
	}
}

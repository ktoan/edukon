package com.java.backend.handler;

import com.java.backend.exception.BadRequestException;
import com.java.backend.exception.InternalServerException;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		LOG.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
				.body(Map.of("success", false, "statusCode", HttpStatus.BAD_REQUEST.value(), "message", errors,
						"timestamp", LocalDateTime.now()));
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> badRequest(BadRequestException e) {
		LOG.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
				.body(Map.of("success", false, "statusCode", HttpStatus.BAD_REQUEST.value(), "message", e.getMessage(),
						"timestamp", LocalDateTime.now()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> notFound(NotFoundException e) {
		LOG.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
				.body(Map.of("success", false, "statusCode", HttpStatus.BAD_REQUEST.value(), "message", e.getMessage(),
						"timestamp", LocalDateTime.now()));
	}

	@ExceptionHandler(NotAccessException.class)
	public ResponseEntity<Object> notAccess(NotAccessException e) {
		LOG.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
				.body(Map.of("success", false, "statusCode", HttpStatus.FORBIDDEN.value(), "message", e.getMessage(),
						"timestamp", LocalDateTime.now()));
	}

	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<Object> internalServer(InternalServerException e) {
		LOG.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.body(Map.of("success", false, "statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message",
						e.getMessage(), "timestamp", LocalDateTime.now()));
	}
}

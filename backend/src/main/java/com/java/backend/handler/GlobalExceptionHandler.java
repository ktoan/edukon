package com.java.backend.handler;

import com.java.backend.exception.BadRequestException;
import com.java.backend.exception.NotAccessException;
import com.java.backend.response.ExceptionResponse;
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

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ExceptionResponse response = new
                ExceptionResponse(false, HttpStatus.BAD_REQUEST.value(), errors, LocalDateTime.now());
        LOG.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequest(BadRequestException e) {
        ExceptionResponse response = new
                ExceptionResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
        LOG.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(NotAccessException.class)
    public ResponseEntity<Object> notAccess(NotAccessException e) {
        ExceptionResponse response = new
                ExceptionResponse(false, HttpStatus.FORBIDDEN.value(), e.getMessage(), LocalDateTime.now());
        LOG.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response);
    }
}

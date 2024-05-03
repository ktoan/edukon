package com.java.backend.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private boolean success;
    private int statusCode;
    private Object message;
    private LocalDateTime date;
}

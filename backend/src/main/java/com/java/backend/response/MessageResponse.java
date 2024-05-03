package com.java.backend.response;

import lombok.*;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private boolean success;
    private String message;
}

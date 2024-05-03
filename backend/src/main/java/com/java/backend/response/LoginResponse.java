package com.java.backend.response;

import com.java.backend.dto.UserDto;
import lombok.*;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private boolean success;
    private String token;
    private UserDto user;
}

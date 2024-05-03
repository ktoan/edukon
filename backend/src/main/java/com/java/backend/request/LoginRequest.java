package com.java.backend.request;

import com.java.backend.constant.ExceptionMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Data
public class LoginRequest {
    @NotNull(message = ExceptionMessage.REQUIRED_EMAIL)
    @NotBlank(message = ExceptionMessage.REQUIRED_EMAIL)
    private String email;
    @NotNull(message = ExceptionMessage.REQUIRED_PASSWORD)
    @NotBlank(message = ExceptionMessage.REQUIRED_PASSWORD)
    private String password;
}

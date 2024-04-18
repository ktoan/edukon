package com.java.backend.request;

import com.java.backend.constant.ExceptionMessage;
import com.java.backend.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Data
public class RegisterRequest {
    @NotNull(message = ExceptionMessage.REQUIRED_EMAIL)
    @NotBlank(message = ExceptionMessage.REQUIRED_EMAIL)
    @Email(message = ExceptionMessage.VALID_EMAIL)
    private String email;
    @NotNull(message = ExceptionMessage.REQUIRED_PASSWORD)
    @NotBlank(message = ExceptionMessage.REQUIRED_PASSWORD)
    @Size(min = 6, message = ExceptionMessage.MIN_PASSWORD)
    private String password;
    @NotNull(message = ExceptionMessage.REQUIRED_NAME)
    @NotBlank(message = ExceptionMessage.REQUIRED_NAME)
    private String name;
    @NotNull(message = ExceptionMessage.REQUIRED_GENDER)
    @Pattern(regexp = "^(MALE|FEMALE|DISCLOSED)$", message = ExceptionMessage.VALID_GENDER)
    private String gender;
}

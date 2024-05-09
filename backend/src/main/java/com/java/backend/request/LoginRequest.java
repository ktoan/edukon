package com.java.backend.request;

import com.java.backend.annotation.Required;
import lombok.Data;

@Data
public class LoginRequest {
    @Required(fieldName = "Email")
    private String email;
    @Required(fieldName = "Password")
    private String password;
}

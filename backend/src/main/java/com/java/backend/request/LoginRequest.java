package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.Required;
import lombok.Data;

@Data
public class LoginRequest {
	@Required(fieldName = "Email")
	@JsonProperty("email")
	private String email;
	@Required(fieldName = "Password")
	@JsonProperty("password")
	private String password;
}

package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.Required;
import lombok.Data;

@Data
public class ConfirmAccountRequest {
	@Required(fieldName = "Email")
	@JsonProperty("email")
	private String email;
	@Required(fieldName = "Token")
	@JsonProperty("token")
	private String token;
}

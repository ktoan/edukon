package com.java.backend.request;

import com.java.backend.annotation.Required;
import lombok.Data;

@Data
public class ConfirmAccountRequest {
	@Required(fieldName = "Email")
	private String email;
	@Required(fieldName = "Token")
	private String token;
}

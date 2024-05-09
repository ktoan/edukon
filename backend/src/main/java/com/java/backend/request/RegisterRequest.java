package com.java.backend.request;

import com.java.backend.annotation.MinMaxSize;
import com.java.backend.annotation.Required;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
	@Required(fieldName = "Email")
	@Email(message = "Email is invalid!")
	private String email;
	@Required(fieldName = "Password")
	@MinMaxSize(min = 6, fieldName = "Password")
	private String password;
	@Required(fieldName = "Full name")
	private String name;
	@Required(fieldName = "Gender")
	@Pattern(regexp = "^(MALE|FEMALE|DISCLOSED)$", message = "Gender is invalid!")
	private String gender;
}

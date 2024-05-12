package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.MinMaxSize;
import com.java.backend.annotation.Required;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
	@Required(fieldName = "Email")
	@Email(message = "Email is invalid!")
	@JsonProperty("email")
	private String email;
	@Required(fieldName = "Password")
	@MinMaxSize(min = 6, fieldName = "Password")
	@JsonProperty("password")
	private String password;
	@Required(fieldName = "Full name")
	@JsonProperty("full_name")
	private String name;
	@Required(fieldName = "Gender")
	@Pattern(regexp = "^(MALE|FEMALE|DISCLOSED)$", message = "Gender is invalid!")
	@JsonProperty("gender")
	private String gender;
}

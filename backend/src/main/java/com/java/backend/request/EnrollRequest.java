package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.Required;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollRequest {
	@Required(fieldName = "Course")
	@JsonProperty("course_id")
	private Integer courseId;
	@Required(fieldName = "Payment method")
	@Pattern(regexp = "^(PAYPAL|STRIPE)$", message = "Payment method is invalid!")
	@JsonProperty("payment_method")
	private String paymentMethod;
}

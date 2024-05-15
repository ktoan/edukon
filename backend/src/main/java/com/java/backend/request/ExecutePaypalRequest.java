package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.Required;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExecutePaypalRequest {
	@JsonProperty("payment_id")
	@Required(fieldName = "Payment id")
	private String paymentId;
	@JsonProperty("payer_id")
	@Required(fieldName = "Payer id")
	private String payerId;
	@JsonProperty("course_id")
	@Required(fieldName = "Course")
	private Integer courseId;
}

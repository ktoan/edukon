package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExecutePaypalRequest {
	@JsonProperty("payment_id")
	private String paymentId;
	@JsonProperty("payer_id")
	private String payerId;
	@JsonProperty("course_id")
	private Integer courseId;
}

package com.java.backend.service;

import com.java.backend.entity.EnrollEntity;
import com.java.backend.request.EnrollRequest;
import com.java.backend.request.ExecutePaypalRequest;

public interface EnrollService {
	EnrollEntity saveEnroll(EnrollEntity enroll);

	String createPayment(EnrollRequest enrollRequest);

	boolean isEnrollCourse(String paymentMethod, ExecutePaypalRequest executePaypalRequest);
	boolean isUserEnrolled(Integer courseId);
}

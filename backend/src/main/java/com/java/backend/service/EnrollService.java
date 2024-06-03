package com.java.backend.service;

import com.java.backend.dto.CourseDto;
import com.java.backend.entity.EnrollEntity;
import com.java.backend.request.EnrollRequest;
import com.java.backend.request.ExecutePaypalRequest;

import java.util.List;

public interface EnrollService {
	List<CourseDto> getEnrolledCourse();
	EnrollEntity saveEnroll(EnrollEntity enroll);

	String createPayment(EnrollRequest enrollRequest);

	boolean isEnrollCourse(String paymentMethod, ExecutePaypalRequest executePaypalRequest);
}

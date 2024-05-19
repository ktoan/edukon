package com.java.backend.service.impl;

import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.EnrollEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.enums.PaymentMethod;
import com.java.backend.exception.BadRequestException;
import com.java.backend.exception.InternalServerException;
import com.java.backend.repository.EnrollRepository;
import com.java.backend.request.EnrollRequest;
import com.java.backend.request.ExecutePaypalRequest;
import com.java.backend.service.CourseService;
import com.java.backend.service.EnrollService;
import com.java.backend.service.UserService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.PaymentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {
	private final EnrollRepository enrollRepository;
	private final CourseService courseService;
	private final PaymentUtil paymentUtil;
	private final ContextUtil contextUtil;
	private final UserService userService;

	@Override
	public EnrollEntity saveEnroll(EnrollEntity enroll) {
		return enrollRepository.save(enroll);
	}

	@Override
	public String createPayment(EnrollRequest enrollRequest) {
		CourseEntity course = courseService.findCourseEntityById(enrollRequest.getCourseId());

		if (contextUtil.loadUserFromContext().getEnrolls().stream()
				.anyMatch(c -> Objects.equals(c.getId(), course.getId()))) {
			throw new BadRequestException("User currently enroll this course!");
		}

		if (PaymentMethod.valueOf(enrollRequest.getPaymentMethod()) == PaymentMethod.PAYPAL) {
			return paymentUtil.createPaypalPayment(course.getPrice(), "USD", course.getId());
		}

		throw new InternalServerException("Something occurred while creating payment!");
	}

	@Override
	public boolean isEnrollCourse(String paymentMethod, ExecutePaypalRequest executePaypalRequest) {
		EnrollEntity enroll = null;
		if (PaymentMethod.PAYPAL.name().equals(paymentMethod) && paymentUtil.isExecutePaypalPayment(
				executePaypalRequest)) {
			enroll = new EnrollEntity();
			enroll.setCourse(courseService.findCourseEntityById(executePaypalRequest.getCourseId()));
			enroll.setUser(contextUtil.loadUserFromContext());
			enroll = saveEnroll(enroll);
			UserEntity user = contextUtil.loadUserFromContext();
			user.getEnrolls().add(enroll);
			userService.saveUser(user);
		}
		// Others payment method
		return enroll != null;
	}
}



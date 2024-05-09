package com.java.backend.validator;

import com.java.backend.annotation.MinMaxSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.MessageSource;

public class MinMaxSizeValidator implements ConstraintValidator<MinMaxSize, String> {
	private int min;
	private int max;
	private String fieldName;
	private final MessageSource messageSource;

	public MinMaxSizeValidator(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void initialize(MinMaxSize constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
		this.fieldName = constraintAnnotation.fieldName();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		boolean isMinValid = (min == -1) || (value.length() >= min);
		boolean isMaxValid = (max == -1) || (value.length() <= max);

		if (!isMinValid || !isMaxValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(buildMessage()).addConstraintViolation();
			return false;
		}
		return true;
	}

	private String buildMessage() {
		if (min == -1) {
			return fieldName + " must be up to " + max + " characters long.";
		} else if (max == -1) {
			return fieldName + " must be at least " + min + " characters long.";
		} else {
			return fieldName + " must be between " + min + " and " + max + " characters long.";
		}
	}
}

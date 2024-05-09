package com.java.backend.validator;

import com.java.backend.annotation.Required;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public class RequiredValidator implements ConstraintValidator<Required, Object> {
	private String fieldName;
	private final MessageSource messageSource;

	@Override
	public void initialize(Required required) {
		this.fieldName = required.fieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean isValid = true;
		if (value == null) {
			isValid = false;
		}
		if ("".equals(value)) {
			isValid = false;
		}
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
							messageSource.getMessage("msg.required", new Object[]{fieldName}, Locale.ENGLISH))
					.addConstraintViolation();
		}
		return isValid;
	}
}

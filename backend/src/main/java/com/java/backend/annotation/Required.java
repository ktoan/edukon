package com.java.backend.annotation;

import com.java.backend.validator.RequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredValidator.class)
public @interface Required {
	String message() default "{msg.required}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fieldName() default "";
}

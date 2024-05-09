package com.java.backend.annotation;

import com.java.backend.validator.MinMaxSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinMaxSizeValidator.class)
public @interface MinMaxSize {
	String message() default "{msg.min-max-size}";

	int min() default -1;

	int max() default -1;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fieldName() default "";
}

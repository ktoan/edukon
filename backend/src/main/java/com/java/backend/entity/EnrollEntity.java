package com.java.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
public class EnrollEntity extends AbstractEntity {
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private UserEntity user;
	@ManyToOne
	@JoinColumn(nullable = false, name = "course_id")
	private CourseEntity course;
}

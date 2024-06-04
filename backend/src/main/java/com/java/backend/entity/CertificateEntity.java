package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "certificates")
@Getter
@Setter
public class CertificateEntity extends AbstractEntity {
	@Column(nullable = false)
	private String source;
	@ManyToOne
	@JoinColumn(nullable = false, name = "student_id")
	private UserEntity user;
	@ManyToOne
	@JoinColumn(nullable = false, name = "course_id")
	private CourseEntity course;
	@Column(nullable = false)
	private Date expiredAt;
}

package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "submissions")
@Getter
@Setter
public class SubmissionEntity extends AbstractEntity {
	@Column(nullable = false)
	private String source;
	@ManyToOne
	@JoinColumn(nullable = false, name = "assignment_id")
	private AssignmentEntity assignment;
	@OneToOne
	@JoinColumn(nullable = false, name = "student_id")
	private UserEntity student;
	private Double grade;
}

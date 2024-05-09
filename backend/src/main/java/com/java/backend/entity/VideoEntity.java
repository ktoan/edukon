package com.java.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Getter
@Setter
public class VideoEntity extends AbstractEntity {
	private String title;
	private String preDescription;
	private String source;
	@ManyToOne
	@JoinColumn(nullable = false, name = "course_id")
	private CourseEntity course;
}

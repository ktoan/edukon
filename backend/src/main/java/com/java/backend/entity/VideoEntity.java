package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
	@OneToOne(mappedBy = "video")
	@JoinColumn(name = "assignment_id")
	private AssignmentEntity assignment;
	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TrackingProgressEntity> trackingProgresses = new HashSet<>();
}

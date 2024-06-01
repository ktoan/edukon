package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignments")
@Getter
@Setter
public class AssignmentEntity extends AbstractEntity {
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String requirement;
	@Column(nullable = false, columnDefinition = "text")
	private String content;
	@Column(nullable = false)
	private String source;
	@OneToOne
	@JoinColumn(nullable = false, name = "video_id")
	private VideoEntity video;
	@Column(nullable = false)
	private LocalDate deadline;
	@OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SubmissionEntity> submissions = new ArrayList<>();
}

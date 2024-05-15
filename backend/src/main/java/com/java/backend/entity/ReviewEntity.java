package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class ReviewEntity extends AbstractEntity {
	private String comment;
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private UserEntity user;
	@ManyToOne
	@JoinColumn(nullable = false, name = "course_id")
	private CourseEntity course;
	private double rating;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private ReviewEntity parent;
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ReviewEntity> childList = new HashSet<>();

	public void removeChildReview(Integer childId) {
		this.childList.removeIf(child -> Objects.equals(child.getId(), childId));
	}
}

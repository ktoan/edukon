package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class CourseEntity extends AbstractEntity {
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String preDescription;
	@Column(nullable = false, columnDefinition = "text")
	private String content;
	@Column(nullable = false)
	private String thumbnail;
	private double price;
	@ManyToOne
	@JoinColumn(nullable = false, name = "instructor_id")
	private UserEntity instructor;
	@ManyToOne
	@JoinColumn(nullable = false, name = "category_id")
	private CategoryEntity category;
	private boolean isApproved;
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<VideoEntity> videos = new ArrayList<>();
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Where(clause = "parent_id is null")
	private List<ReviewEntity> reviews = new ArrayList<>();
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<CertificateEntity> certificates = new ArrayList<>();

	public void removeVideo(Integer videoId) {
		this.videos.removeIf(video -> Objects.equals(video.getId(), videoId));
	}

	public void removeReview(Integer reviewId) {
		this.reviews.removeIf(review -> Objects.equals(review.getId(), reviewId));
	}
}

package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
	private Set<VideoEntity> videos = new HashSet<>();

	public void removeVideo(Integer videoId) {
		this.videos.removeIf(video -> Objects.equals(video.getId(), videoId));
	}
}

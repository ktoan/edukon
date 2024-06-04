package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "blogs")
@Getter
@Setter
public class BlogEntity extends AbstractEntity {
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String preDescription;
	@Column(columnDefinition = "text", nullable = false)
	private String content;
	@Column(nullable = false)
	private String thumbnail;
	private boolean isApproved;
	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false)
	private UserEntity author;
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private CategoryEntity category;
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Where(clause = "parent_id is null")
	private Set<CommentEntity> comments = new HashSet<>();

	public void removeComment(Integer commentId) {
		this.comments.removeIf(comment -> Objects.equals(comment.getId(), commentId));
	}
}

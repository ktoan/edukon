package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class CommentEntity extends AbstractEntity {
	@Column(nullable = false)
	private String comment;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	@ManyToOne
	@JoinColumn(name = "blog_id", nullable = false)
	private BlogEntity blog;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private CommentEntity parent;
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CommentEntity> childList = new HashSet<>();

	public void removeChildComment(Integer childId) {
		this.childList.removeIf(child -> Objects.equals(child.getId(), childId));
	}
}

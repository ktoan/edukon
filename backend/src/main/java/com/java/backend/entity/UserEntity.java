package com.java.backend.entity;

import com.java.backend.enums.Gender;
import com.java.backend.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class UserEntity extends AbstractEntity implements UserDetails {
	private String email;
	private String password;
	private String name;
	private String avatar;
	private String phone;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean isActivated;
	private boolean isLocked;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ConfirmationTokenEntity> confirmationTokens = new HashSet<>();
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<BlogEntity> blogs = new HashSet<>();
	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CourseEntity> courses = new HashSet<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EnrollEntity> enrolls = new HashSet<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CommentEntity> comments = new HashSet<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ReviewEntity> reviews = new HashSet<>();

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActivated;
	}

	public void removeBlog(Integer blogId) {
		this.blogs.removeIf(blog -> Objects.equals(blog.getId(), blogId));
	}

	public void removeCourse(Integer courseId) {
		this.courses.removeIf(course -> Objects.equals(course.getId(), courseId));
	}

	public void removeComment(Integer commentId) {
		this.comments.removeIf(comment -> Objects.equals(comment.getId(), commentId));
	}

	public void removeReview(Integer reviewId) {
		this.reviews.removeIf(review -> Objects.equals(review.getId(), reviewId));
	}
}

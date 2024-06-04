package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "confirmation_tokens")
@Getter
@Setter
public class ConfirmationTokenEntity extends AbstractEntity {
	@Column(nullable = false, unique = true)
	private String token;
	@Column(nullable = false)
	private Date expiredAt;
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private UserEntity user;
}

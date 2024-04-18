package com.java.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "tokens")
@Getter
@Setter
public class TokenEntity extends AbstractEntity {
    private String tokenValue;
    private Date expiredAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;
}

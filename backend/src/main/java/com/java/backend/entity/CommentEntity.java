package com.java.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "comments")
@Getter
@Setter
public class CommentEntity extends AbstractEntity {
    private String comment;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "blog_id")
    private BlogEntity blog;
}

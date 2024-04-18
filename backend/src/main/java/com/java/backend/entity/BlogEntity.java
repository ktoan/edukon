package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "blogs")
@Getter
@Setter
public class BlogEntity extends AbstractEntity {
    private String title;
    private String preDescription;
    @Column(columnDefinition = "text")
    private String content;
    private String image;
    private Boolean isApproved;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "parent_id is null")
    private Set<CommentEntity> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity author;
}

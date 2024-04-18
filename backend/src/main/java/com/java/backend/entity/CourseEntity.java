package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "courses")
@Getter
@Setter
public class CourseEntity extends AbstractEntity{
    private String name;
    private String preDescription;
    private String overview;
    @Column(columnDefinition = "text")
    private String content;
    private Boolean isApproved;
    private Double price;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<VideoEntity> videos;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<EnrollmentEntity> enrollments;
}

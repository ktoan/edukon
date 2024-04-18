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
@Table(name = "videos")
@Getter
@Setter
public class VideoEntity extends AbstractEntity {
    private String title;
    private String source;
    private String preDescription;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseEntity course;

    @OneToOne(mappedBy = "video", fetch = FetchType.EAGER)
    private AssignmentEntity assignment;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private List<TrackingProcessEntity> trackingProcesses;
}

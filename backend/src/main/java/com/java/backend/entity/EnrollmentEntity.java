package com.java.backend.entity;

import com.java.backend.enums.StudyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "enrollments")
@Getter
@Setter
public class EnrollmentEntity extends AbstractEntity{
    @Enumerated(EnumType.STRING)
    private StudyStatus studyStatus;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseEntity course;
}

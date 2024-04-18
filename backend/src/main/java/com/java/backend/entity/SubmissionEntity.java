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
@Table(name = "submissions")
@Getter
@Setter
public class SubmissionEntity extends AbstractEntity {
    private String source;

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "id")
    private AssignmentEntity assignment;
}

package com.java.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "assignments")
@Getter
@Setter
public class AssignmentEntity extends AbstractEntity {
    private String title;
    @Column(columnDefinition = "text")
    private String requirement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video", referencedColumnName = "id")
    private VideoEntity video;
}

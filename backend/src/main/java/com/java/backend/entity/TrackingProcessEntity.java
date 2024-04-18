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
@Table(name = "tracking_processes")
@Getter
@Setter
public class TrackingProcessEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private VideoEntity video;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private UserEntity user;
}

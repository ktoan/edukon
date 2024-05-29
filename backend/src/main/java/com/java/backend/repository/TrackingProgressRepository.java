package com.java.backend.repository;

import com.java.backend.entity.TrackingProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingProgressRepository extends JpaRepository<TrackingProgressEntity, Integer> {
}

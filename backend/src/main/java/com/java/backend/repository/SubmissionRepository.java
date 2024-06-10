package com.java.backend.repository;

import com.java.backend.entity.AssignmentEntity;
import com.java.backend.entity.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Integer> {
	Optional<SubmissionEntity> findByAssignment(AssignmentEntity assignment);
}

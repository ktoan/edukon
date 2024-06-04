package com.java.backend.repository;

import com.java.backend.entity.CertificateEntity;
import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Integer> {
    boolean existsByCourseAndUser(CourseEntity course, UserEntity user);
}

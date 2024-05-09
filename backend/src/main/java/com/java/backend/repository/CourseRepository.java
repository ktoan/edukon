package com.java.backend.repository;

import com.java.backend.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
	List<CourseEntity> findByNameContainingOrCategoryNameContainingOrInstructorNameContaining(String nameKeyword,
	                                                                                          String categoryKeyword,
	                                                                                          String instructorKeyword);
}

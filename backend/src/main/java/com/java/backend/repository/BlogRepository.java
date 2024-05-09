package com.java.backend.repository;

import com.java.backend.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
	List<BlogEntity> findByTitleContainingOrCategoryNameContainingOrAuthorNameContaining(String titleKeyword,
	                                                                                     String categoryKeyword,
	                                                                                     String authorKeyword);
}

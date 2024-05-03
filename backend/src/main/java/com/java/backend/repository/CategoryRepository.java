package com.java.backend.repository;

import com.java.backend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}

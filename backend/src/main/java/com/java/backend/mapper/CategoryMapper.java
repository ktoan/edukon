package com.java.backend.mapper;

import com.java.backend.dto.CategoryDto;
import com.java.backend.entity.CategoryEntity;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
public interface CategoryMapper {
    CategoryEntity toEntity(CategoryDto categoryDto);
    CategoryDto toDto(CategoryEntity category);
}

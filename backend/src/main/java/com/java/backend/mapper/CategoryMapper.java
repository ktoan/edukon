package com.java.backend.mapper;

import com.java.backend.dto.CategoryDto;
import com.java.backend.entity.CategoryEntity;

public interface CategoryMapper {
    CategoryEntity toEntity(CategoryDto categoryDto);
    CategoryDto toDto(CategoryEntity category);
}

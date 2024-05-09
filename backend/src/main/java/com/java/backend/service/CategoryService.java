package com.java.backend.service;

import com.java.backend.dto.CategoryDto;
import com.java.backend.entity.CategoryEntity;
import com.java.backend.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryEntity saveCategory(CategoryEntity category);

    List<CategoryDto> findAllCategories();

    CategoryDto createCategory(CategoryRequest categoryRequest);

    CategoryDto updateCategory(Integer categoryId, CategoryRequest categoryRequest);

    void deleteCategory(Integer categoryId);

    CategoryEntity findCategoryEntityById(Integer categoryId);
}

package com.java.backend.service.impl;

import com.java.backend.constant.ExceptionMessage;
import com.java.backend.dto.CategoryDto;
import com.java.backend.entity.CategoryEntity;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.CategoryMapper;
import com.java.backend.repository.CategoryRepository;
import com.java.backend.request.CategoryRequest;
import com.java.backend.service.CategoryService;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileUtil fileUtil;

    @Override
    public CategoryEntity saveCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setName(categoryRequest.getName());
        newCategory.setThumbnail(fileUtil.uploadImage(categoryRequest.getThumbnail(), "category"));

        return categoryMapper.toDto(saveCategory(newCategory));
    }

    @Override
    public CategoryDto updateCategory(Integer categoryId, CategoryRequest categoryRequest) {
        Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessage.NOT_FOUND, "Category id", categoryId));
        }

        CategoryEntity updatedCategory = category.get();
        if (StringUtils.isEmpty(categoryRequest.getName())) {
            updatedCategory.setName(categoryRequest.getName());
        }
        if (categoryRequest.getThumbnail() != null) {
            updatedCategory.setThumbnail(fileUtil.uploadImage(categoryRequest.getThumbnail(), "category"));
        }

        return categoryMapper.toDto(saveCategory(updatedCategory));
    }
}

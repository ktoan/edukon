package com.java.backend.service.impl;

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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final FileUtil fileUtil;
	private final MessageSource messageSource;

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
		CategoryEntity updatedCategory = findCategoryEntityById(categoryId);
		if (!StringUtils.isEmpty(categoryRequest.getName())) {
			updatedCategory.setName(categoryRequest.getName());
		}
		if (categoryRequest.getThumbnail() != null) {
			updatedCategory.setThumbnail(fileUtil.uploadImage(categoryRequest.getThumbnail(), "category"));
		}

		return categoryMapper.toDto(saveCategory(updatedCategory));
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		CategoryEntity deletedCategory = findCategoryEntityById(categoryId);
		categoryRepository.delete(deletedCategory);
	}

	@Override
	public CategoryEntity findCategoryEntityById(Integer categoryId) {
		Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
		if (category.isEmpty()) {
			throw new NotFoundException(
					messageSource.getMessage("msg.not-found", new Object[]{"Category id", categoryId}, Locale.ENGLISH));
		}

		return category.get();
	}
}

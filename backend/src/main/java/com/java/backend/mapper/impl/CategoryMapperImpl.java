package com.java.backend.mapper.impl;

import com.java.backend.dto.CategoryDto;
import com.java.backend.entity.CategoryEntity;
import com.java.backend.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {
    private final ModelMapper modelMapper;

    @Override
    public CategoryEntity toEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, CategoryEntity.class);
    }

    @Override
    public CategoryDto toDto(CategoryEntity category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}

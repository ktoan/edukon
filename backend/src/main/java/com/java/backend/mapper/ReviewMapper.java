package com.java.backend.mapper;

import com.java.backend.dto.ReviewDto;
import com.java.backend.entity.ReviewEntity;

public interface ReviewMapper {
	ReviewEntity toEntity(ReviewDto reviewDto);

	ReviewDto toDto(ReviewEntity review);
}

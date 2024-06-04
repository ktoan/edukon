package com.java.backend.mapper.impl;

import com.java.backend.dto.ReviewDto;
import com.java.backend.entity.ReviewEntity;
import com.java.backend.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapperImpl implements ReviewMapper {
	private final ModelMapper modelMapper;

	@Override
	public ReviewEntity toEntity(ReviewDto reviewDto) {
		return modelMapper.map(reviewDto, ReviewEntity.class);
	}

	@Override
	public ReviewDto toDto(ReviewEntity review) {
		return modelMapper.map(review, ReviewDto.class);
	}
}

package com.java.backend.mapper.impl;

import com.java.backend.dto.CourseDto;
import com.java.backend.entity.CourseEntity;
import com.java.backend.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapperImpl implements CourseMapper {
	private final ModelMapper modelMapper;
	@Override
	public CourseEntity toEntity(CourseDto courseDto) {
		return modelMapper.map(courseDto, CourseEntity.class);
	}

	@Override
	public CourseDto toDto(CourseEntity course) {
		return modelMapper.map(course, CourseDto.class);
	}
}

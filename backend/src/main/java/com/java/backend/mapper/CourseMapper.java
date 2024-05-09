package com.java.backend.mapper;

import com.java.backend.dto.CourseDto;
import com.java.backend.entity.CourseEntity;

public interface CourseMapper {
	CourseEntity toEntity(CourseDto courseDto);

	CourseDto toDto(CourseEntity course);
}

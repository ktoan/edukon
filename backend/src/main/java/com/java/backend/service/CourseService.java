package com.java.backend.service;

import com.java.backend.dto.BlogDto;
import com.java.backend.dto.CourseDto;
import com.java.backend.entity.CourseEntity;
import com.java.backend.request.CourseRequest;

import java.util.List;

public interface CourseService {
	CourseEntity saveCourse(CourseEntity course);
	List<CourseDto> findAllCourses(String keyword);

	CourseDto createCourse(CourseRequest courseRequest);

	CourseDto updateCourse(Integer courseId, CourseRequest courseRequest);

	void deleteCourse(Integer courseId);

	CourseEntity findCourseEntityById(Integer courseId);
}

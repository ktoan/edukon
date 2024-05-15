package com.java.backend.service.impl;

import com.java.backend.dto.CourseDto;
import com.java.backend.entity.CategoryEntity;
import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.CourseMapper;
import com.java.backend.repository.CourseRepository;
import com.java.backend.request.CourseRequest;
import com.java.backend.service.CategoryService;
import com.java.backend.service.CourseService;
import com.java.backend.service.UserService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	private final ContextUtil contextUtil;
	private final FileUtil fileUtil;
	private final UserService userService;
	private final CategoryService categoryService;
	private final MessageSource messageSource;

	@Override
	public CourseEntity saveCourse(CourseEntity course) {
		return courseRepository.save(course);
	}

	@Override
	public List<CourseDto> findAllCourses(String keyword) {
		List<CourseEntity> courses;
		if (StringUtils.isEmpty(keyword)) {
			courses = courseRepository.findAll();
		} else {
			courses = courseRepository.findByNameContainingOrCategoryNameContainingOrInstructorNameContaining(keyword,
					keyword, keyword);
		}
		return courses.stream().map(courseMapper::toDto).toList();
	}

	@Override
	public CourseDto getCourseById(Integer courseId) {
		return courseMapper.toDto(findCourseEntityById(courseId));
	}

	@Override
	public CourseDto createCourse(CourseRequest courseRequest) {
		CourseEntity newCourse = new CourseEntity();
		newCourse.setName(courseRequest.getName());
		newCourse.setPreDescription(courseRequest.getPreDescription());
		newCourse.setContent(courseRequest.getContent());
		newCourse.setPrice(courseRequest.getPrice());
		newCourse.setThumbnail(fileUtil.uploadImage(courseRequest.getThumbnail(), "course"));
		newCourse.setApproved(false);
		CategoryEntity category = categoryService.findCategoryEntityById(courseRequest.getCategoryId());
		newCourse.setCategory(category);
		newCourse.setInstructor(contextUtil.loadUserFromContext());
		return courseMapper.toDto(saveCourse(newCourse));
	}

	@Override
	public CourseDto updateCourse(Integer courseId, CourseRequest courseRequest) {
		CourseEntity updatedCourse = findCourseEntityById(courseId);
		if (!Objects.equals(updatedCourse.getInstructor().getId(), contextUtil.loadUserFromContext().getId())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		if (!StringUtils.isEmpty(courseRequest.getName())) {
			updatedCourse.setName(courseRequest.getName());
		}
		if (!StringUtils.isEmpty(courseRequest.getPreDescription())) {
			updatedCourse.setPreDescription(courseRequest.getPreDescription());
		}
		if (!StringUtils.isEmpty(courseRequest.getPreDescription())) {
			updatedCourse.setPreDescription(courseRequest.getPreDescription());
		}
		if (!StringUtils.isEmpty(courseRequest.getContent())) {
			updatedCourse.setContent(courseRequest.getContent());
		}
		if (courseRequest.getThumbnail() != null) {
			updatedCourse.setThumbnail(fileUtil.uploadImage(courseRequest.getThumbnail(), "course"));
		}
		if (courseRequest.getPrice() != null) {
			updatedCourse.setPrice(courseRequest.getPrice());
		}
		if (courseRequest.getCategoryId() != null) {
			CategoryEntity category = categoryService.findCategoryEntityById(courseRequest.getCategoryId());
			updatedCourse.setCategory(category);
		}
		return courseMapper.toDto(saveCourse(updatedCourse));
	}

	@Override
	public void deleteCourse(Integer courseId) {
		CourseEntity deletedCourse = findCourseEntityById(courseId);
		if (!contextUtil.isAdminRole() && !Objects.equals(deletedCourse.getInstructor().getId(),
				contextUtil.loadUserFromContext().getId())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		UserEntity instructor = deletedCourse.getInstructor();
		instructor.removeCourse(courseId);
		userService.saveUser(instructor);
		courseRepository.delete(deletedCourse);
	}

	@Override
	public CourseEntity findCourseEntityById(Integer courseId) {
		Optional<CourseEntity> course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new NotFoundException(
					messageSource.getMessage("msg.not-found", new Object[]{"Course id", courseId}, Locale.ENGLISH));
		}
		return course.get();
	}
}

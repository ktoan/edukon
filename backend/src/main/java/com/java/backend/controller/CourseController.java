package com.java.backend.controller;

import com.java.backend.dto.CourseDto;
import com.java.backend.request.CourseRequest;
import com.java.backend.service.CourseService;
import com.java.backend.service.EnrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CourseController {
	private final CourseService courseService;
	private final EnrollService enrollService;

	@GetMapping("")
	public ResponseEntity<Object> findAllBlogs(@RequestParam(required = false) String keyword) {
		List<CourseDto> courses = courseService.findAllCourses(keyword);
		return new ResponseEntity<>(Map.of("success", true, "courses", courses), HttpStatus.OK);
	}

	@GetMapping("/detail")
	public ResponseEntity<Object> getDetailsCourse(@RequestParam Integer courseId) {
		CourseDto course = courseService.getCourseById(courseId);
		return new ResponseEntity<>(
				Map.of("success", true, "course", course, "is_enrolled", enrollService.isUserEnrolled(courseId)),
				HttpStatus.OK);
	}

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> createCourse(@Valid @ModelAttribute CourseRequest courseRequest) {
		CourseDto newCourse = courseService.createCourse(courseRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_course", newCourse), HttpStatus.CREATED);
	}

	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updateCourse(@RequestParam Integer courseId,
	                                           @ModelAttribute CourseRequest courseRequest) {
		CourseDto updatedCourse = courseService.updateCourse(courseId, courseRequest);
		return new ResponseEntity<>(Map.of("success", true, "updated_course", updatedCourse), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteCourse(@RequestParam Integer courseId) {
		courseService.deleteCourse(courseId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Course deleted successfully!"), HttpStatus.OK);
	}
}

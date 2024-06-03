package com.java.backend.controller;

import com.java.backend.dto.BlogDto;
import com.java.backend.request.BlogRequest;
import com.java.backend.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Blog")
public class BlogController {
	private final BlogService blogService;

	@GetMapping("")
	@Operation(summary = "Get all blogs with keyword")
	public ResponseEntity<?> findAllBlogs(@RequestParam(required = false) String keyword) {
		List<BlogDto> blogs = blogService.findAllBlogs(keyword);
		return new ResponseEntity<>(Map.of("success", true, "blogs", blogs), HttpStatus.OK);
	}

	@GetMapping("/detail")
	@Operation(summary = "Get details blog by id")
	public ResponseEntity<?> getDetailsBlog(@RequestParam Integer blogId) {
		BlogDto blog = blogService.getBlogById(blogId);
		return new ResponseEntity<>(Map.of("success", true, "blog", blog), HttpStatus.OK);
	}

	@PostMapping("/create")
	@Operation(summary = "Create new blog")
	public ResponseEntity<?> createBlog(@Valid @ModelAttribute BlogRequest blogRequest) {
		BlogDto newBlog = blogService.createBlog(blogRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_blog", newBlog), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	@Operation(summary = "Update blog by id")
	public ResponseEntity<?> updateBlog(@RequestParam Integer blogId, @ModelAttribute BlogRequest blogRequest) {
		BlogDto updatedBlog = blogService.updateBlog(blogId, blogRequest);
		return new ResponseEntity<>(Map.of("success", true, "updated_blog", updatedBlog), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	@Operation(summary = "Delete blog by id")
	public ResponseEntity<?> deleteBlog(@RequestParam Integer blogId) {
		blogService.deleteBlog(blogId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Blog deleted successfully!"), HttpStatus.OK);
	}
}

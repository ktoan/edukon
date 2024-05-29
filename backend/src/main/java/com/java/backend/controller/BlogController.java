package com.java.backend.controller;

import com.java.backend.dto.BlogDto;
import com.java.backend.request.BlogRequest;
import com.java.backend.service.BlogService;
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
public class BlogController {
	private final BlogService blogService;

	@GetMapping("")
	public ResponseEntity<Object> findAllBlogs(@RequestParam(required = false) String keyword) {
		List<BlogDto> blogs = blogService.findAllBlogs(keyword);
		return new ResponseEntity<>(Map.of("success", true, "blogs", blogs), HttpStatus.OK);
	}

	@GetMapping("/detail")
	public ResponseEntity<Object> getDetailsBlog(@RequestParam Integer blogId) {
		BlogDto blog = blogService.getBlogById(blogId);
		return new ResponseEntity<>(Map.of("success", true, "blog", blog), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createBlog(@Valid @ModelAttribute BlogRequest blogRequest) {
		BlogDto newBlog = blogService.createBlog(blogRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_blog", newBlog), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateBlog(@RequestParam Integer blogId, @ModelAttribute BlogRequest blogRequest) {
		BlogDto updatedBlog = blogService.updateBlog(blogId, blogRequest);
		return new ResponseEntity<>(Map.of("success", true, "updated_blog", updatedBlog), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteBlog(@RequestParam Integer blogId) {
		blogService.deleteBlog(blogId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Blog deleted successfully!"), HttpStatus.OK);
	}
}

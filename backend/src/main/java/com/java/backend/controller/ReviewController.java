package com.java.backend.controller;

import com.java.backend.dto.ReviewDto;
import com.java.backend.request.ReviewRequest;
import com.java.backend.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Review")
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping("/create")
	@Operation(summary = "Create new review")
	public ResponseEntity<Object> createReview(@Valid @RequestBody ReviewRequest reviewRequest) {
		ReviewDto newReview = reviewService.createReview(reviewRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_review", newReview), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	@Operation(summary = "Update review by id")
	public ResponseEntity<Object> updateReview(@RequestParam Integer reviewId,
	                                           @RequestBody ReviewRequest reviewRequest) {
		ReviewDto updatedReview = reviewService.updateReview(reviewId, reviewRequest);
		return new ResponseEntity<>(Map.of("success", true, "updated_review", updatedReview), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	@Operation(summary = "Delete review by id")
	public ResponseEntity<Object> deleteReview(@RequestParam Integer reviewId) {
		reviewService.deleteReview(reviewId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Review deleted successfully!"), HttpStatus.OK);
	}
}

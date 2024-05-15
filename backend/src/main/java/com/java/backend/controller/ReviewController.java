package com.java.backend.controller;

import com.java.backend.dto.ReviewDto;
import com.java.backend.request.ReviewRequest;
import com.java.backend.service.ReviewService;
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
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping("/create")
	public ResponseEntity<Object> createReview(@Valid @RequestBody ReviewRequest reviewRequest) {
		ReviewDto newReview = reviewService.createReview(reviewRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_review", newReview), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateReview(@RequestParam Integer reviewId,
	                                           @RequestBody ReviewRequest reviewRequest) {
		ReviewDto updatedReview = reviewService.updateReview(reviewId, reviewRequest);
		return new ResponseEntity<>(Map.of("success", true, "updated_review", updatedReview), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteReview(@RequestParam Integer reviewId) {
		reviewService.deleteReview(reviewId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Review deleted successfully!"), HttpStatus.OK);
	}
}

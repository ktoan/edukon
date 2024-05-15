package com.java.backend.service;

import com.java.backend.dto.ReviewDto;
import com.java.backend.entity.ReviewEntity;
import com.java.backend.request.ReviewRequest;

public interface ReviewService {
	ReviewEntity saveReview(ReviewEntity review);

	ReviewDto createReview(ReviewRequest reviewRequest);

	ReviewDto updateReview(Integer reviewId, ReviewRequest reviewRequest);

	void deleteReview(Integer reviewId);

	ReviewEntity findReviewEntityById(Integer reviewId);
}

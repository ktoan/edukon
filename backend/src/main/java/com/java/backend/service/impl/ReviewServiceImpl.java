package com.java.backend.service.impl;

import com.java.backend.dto.ReviewDto;
import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.ReviewEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.ReviewMapper;
import com.java.backend.repository.ReviewRepository;
import com.java.backend.request.ReviewRequest;
import com.java.backend.service.CourseService;
import com.java.backend.service.ReviewService;
import com.java.backend.service.UserService;
import com.java.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;
	private final ContextUtil contextUtil;
	private final UserService userService;
	private final CourseService courseService;
	private final MessageSource messageSource;

	@Override
	public ReviewEntity saveReview(ReviewEntity review) {
		return reviewRepository.save(review);
	}

	@Override
	public ReviewDto createReview(ReviewRequest reviewRequest) {
		ReviewEntity newReview = new ReviewEntity();
		newReview.setComment(reviewRequest.getComment());
		newReview.setCourse(courseService.findCourseEntityById(reviewRequest.getCourseId()));
		newReview.setRating(reviewRequest.getRating());
		newReview.setUser(contextUtil.loadUserFromContext());
		if (reviewRequest.getParentId() != null) {
			ReviewEntity parentReview = findReviewEntityById(reviewRequest.getParentId());
			newReview.setParent(parentReview);
		}
		return reviewMapper.toDto(saveReview(newReview));
	}

	@Override
	public ReviewDto updateReview(Integer reviewId, ReviewRequest reviewRequest) {
		ReviewEntity updatedReview = findReviewEntityById(reviewId);
		if (!Objects.equals(updatedReview.getUser().getId(), contextUtil.loadUserFromContext().getId())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		if (!StringUtils.isEmpty(reviewRequest.getComment())) {
			updatedReview.setComment(reviewRequest.getComment());
		}
		if (reviewRequest.getRating() != null) {
			updatedReview.setRating(reviewRequest.getRating());
		}
		return reviewMapper.toDto(reviewRepository.save(updatedReview));
	}

	@Override
	public void deleteReview(Integer reviewId) {
		ReviewEntity deletedReview = findReviewEntityById(reviewId);
		if (!Objects.equals(deletedReview.getUser().getId(), contextUtil.loadUserFromContext().getId())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		CourseEntity course = deletedReview.getCourse();
		course.removeReview(reviewId);
		courseService.saveCourse(course);
		UserEntity user = deletedReview.getUser();
		user.removeReview(reviewId);
		userService.saveUser(user);
		ReviewEntity parentReview = deletedReview.getParent();
		if (parentReview != null) {
			parentReview.removeChildReview(reviewId);
			saveReview(parentReview);
		}
		reviewRepository.save(deletedReview);
	}

	@Override
	public ReviewEntity findReviewEntityById(Integer reviewId) {
		Optional<ReviewEntity> review = reviewRepository.findById(reviewId);
		if (review.isEmpty()) {
			throw new NotFoundException(
					messageSource.getMessage("msg.not-found", new Object[]{"Review id", reviewId}, Locale.ENGLISH));
		}
		return review.get();
	}
}

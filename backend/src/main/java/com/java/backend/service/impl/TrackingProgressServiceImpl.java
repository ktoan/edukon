package com.java.backend.service.impl;

import com.java.backend.entity.TrackingProgressEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.entity.VideoEntity;
import com.java.backend.exception.BadRequestException;
import com.java.backend.repository.TrackingProgressRepository;
import com.java.backend.service.TrackingProgressService;
import com.java.backend.service.VideoService;
import com.java.backend.util.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TrackingProgressServiceImpl implements TrackingProgressService {
	private ContextUtil contextUtil;
	private TrackingProgressRepository trackingProgressRepository;
	private VideoService videoService;

	@Autowired
	public TrackingProgressServiceImpl(ContextUtil contextUtil, TrackingProgressRepository trackingProgressRepository,
	                                   @Lazy VideoService videoService) {
		this.contextUtil = contextUtil;
		this.trackingProgressRepository = trackingProgressRepository;
		this.videoService = videoService;
	}

	@Override
	public TrackingProgressEntity saveTrackingProgress(TrackingProgressEntity trackingProgress) {
		return trackingProgressRepository.save(trackingProgress);
	}

	@Override
	public boolean createTrackingProgress(Integer videoId) {
		UserEntity user = contextUtil.loadUserFromContext();
		VideoEntity video = videoService.findVideoEntityById(videoId);
		if (isTrackedProgress(user, video, videoId)) {
			throw new BadRequestException("Invalid tracking progress request!");
		}
		TrackingProgressEntity trackingProgress = new TrackingProgressEntity();
		trackingProgress.setUser(user);
		trackingProgress.setVideo(video);
		trackingProgress = saveTrackingProgress(trackingProgress);
		return trackingProgress != null;
	}

	@Override
	public boolean isCompleteVideo(Integer videoId) {
		UserEntity user = contextUtil.loadUserFromContext();
		VideoEntity video = videoService.findVideoEntityById(videoId);
		return isTrackedProgress(user, video, videoId);
	}

	private boolean isTrackedProgress(UserEntity user, VideoEntity video, Integer videoId) {
		if (user == null) {
			return false;
		}
		return user.getTrackingProgresses().stream()
				.anyMatch(t -> Objects.equals(t.getVideo().getId(), videoId)) && video.getTrackingProgresses().stream()
				.anyMatch(t -> Objects.equals(t.getUser().getId(), user.getId()));
	}
}

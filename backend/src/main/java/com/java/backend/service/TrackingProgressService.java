package com.java.backend.service;

import com.java.backend.entity.TrackingProgressEntity;

public interface TrackingProgressService {
	TrackingProgressEntity saveTrackingProgress(TrackingProgressEntity trackingProgress);

	boolean createTrackingProgress(Integer videoId);

	boolean isCompleteVideo(Integer videoId);
}

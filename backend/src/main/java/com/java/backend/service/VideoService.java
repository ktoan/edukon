package com.java.backend.service;

import com.java.backend.dto.VideoDto;
import com.java.backend.entity.VideoEntity;
import com.java.backend.request.VideoRequest;

public interface VideoService {
	VideoEntity saveVideo(VideoEntity video);

	VideoDto createVideo(VideoRequest videoRequest);

	VideoDto updateVideo(Integer videoId, VideoRequest videoRequest);

	void deleteVideo(Integer videoId);

	VideoEntity findVideoEntityById(Integer videoId);
}

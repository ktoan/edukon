package com.java.backend.mapper.impl;

import com.java.backend.dto.VideoDto;
import com.java.backend.entity.VideoEntity;
import com.java.backend.mapper.VideoMapper;
import com.java.backend.service.TrackingProgressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class VideoMapperImpl implements VideoMapper {
	private ModelMapper modelMapper;
	private TrackingProgressService trackingProgressService;

	@Autowired
	public VideoMapperImpl(ModelMapper modelMapper, @Lazy TrackingProgressService trackingProgressService) {
		this.modelMapper = modelMapper;
		this.trackingProgressService = trackingProgressService;
	}


	@Override
	public VideoEntity toEntity(VideoDto videoDto) {
		return modelMapper.map(videoDto, VideoEntity.class);
	}

	@Override
	public VideoDto toDto(VideoEntity video) {
		VideoDto videoDto = modelMapper.map(video, VideoDto.class);
		videoDto.setTracked(trackingProgressService.isCompleteVideo(video.getId()));
		return videoDto;
	}
}

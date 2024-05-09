package com.java.backend.mapper.impl;

import com.java.backend.dto.VideoDto;
import com.java.backend.entity.VideoEntity;
import com.java.backend.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapperImpl implements VideoMapper {
	private final ModelMapper modelMapper;

	@Override
	public VideoEntity toEntity(VideoDto videoDto) {
		return modelMapper.map(videoDto, VideoEntity.class);
	}

	@Override
	public VideoDto toDto(VideoEntity video) {
		return modelMapper.map(video, VideoDto.class);
	}
}

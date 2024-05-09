package com.java.backend.mapper;

import com.java.backend.dto.VideoDto;
import com.java.backend.entity.VideoEntity;

public interface VideoMapper {
	VideoEntity toEntity(VideoDto videoDto);

	VideoDto toDto(VideoEntity video);
}

package com.java.backend.mapper.impl;

import com.java.backend.dto.CourseDto;
import com.java.backend.entity.CertificateEntity;
import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.mapper.CourseMapper;
import com.java.backend.mapper.VideoMapper;
import com.java.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CourseMapperImpl implements CourseMapper {
	private final ModelMapper modelMapper;
	private final ContextUtil contextUtil;
	private final VideoMapper videoMapper;

	@Override
	public CourseEntity toEntity(CourseDto courseDto) {
		return modelMapper.map(courseDto, CourseEntity.class);
	}

	@Override
	public CourseDto toDto(CourseEntity course) {
		CourseDto courseDto = modelMapper.map(course, CourseDto.class);
		UserEntity user = contextUtil.loadUserFromContext();
		boolean isEnrolled = user != null && user.getEnrolls().stream()
				.anyMatch(e -> Objects.equals(e.getCourse().getId(), course.getId()));
		courseDto.setEnrolled(isEnrolled);
		courseDto.setVideos(course.getVideos().stream().map(videoMapper::toDto).toList());
		courseDto.setHasCertificate(course.getCertificates().stream()
				.anyMatch(c -> Objects.equals(c.getUser().getId(), contextUtil.loadUserFromContext().getId())));
		CertificateEntity certificate = course.getCertificates().stream()
				.filter(c -> Objects.equals(c.getUser().getId(), contextUtil.loadUserFromContext().getId())).findFirst()
				.orElse(null);
		courseDto.setSourceCertificate(certificate != null ? certificate.getSource() : "");
		return courseDto;
	}
}

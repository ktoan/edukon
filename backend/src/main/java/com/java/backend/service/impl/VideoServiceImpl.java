package com.java.backend.service.impl;

import com.java.backend.dto.VideoDto;
import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.entity.VideoEntity;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.VideoMapper;
import com.java.backend.repository.VideoRepository;
import com.java.backend.request.VideoRequest;
import com.java.backend.service.CourseService;
import com.java.backend.service.VideoService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
	private final CourseService courseService;
	private final VideoRepository videoRepository;
	private final VideoMapper videoMapper;
	private final FileUtil fileUtil;
	private final ContextUtil contextUtil;
	private final MessageSource messageSource;

	@Override
	public VideoEntity saveVideo(VideoEntity video) {
		return videoRepository.save(video);
	}

	@Override
	public VideoDto createVideo(VideoRequest videoRequest) {
		CourseEntity course = courseService.findCourseEntityById(videoRequest.getCourseId());
		if (!isRequestByCourseInstructor(course)) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		VideoEntity newVideo = new VideoEntity();
		newVideo.setTitle(videoRequest.getTitle());
		newVideo.setPreDescription(videoRequest.getPreDescription());
		newVideo.setSource(fileUtil.uploadVideo(videoRequest.getVideo(), "course-video"));
		newVideo.setCourse(course);
		return videoMapper.toDto(saveVideo(newVideo));
	}

	@Override
	public VideoDto updateVideo(Integer videoId, VideoRequest videoRequest) {
		VideoEntity updatedVideo = findVideoEntityById(videoId);
		if (!isRequestByCourseInstructor(updatedVideo.getCourse())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		if (!StringUtils.isEmpty(videoRequest.getTitle())) {
			updatedVideo.setTitle(videoRequest.getTitle());
		}
		if (!StringUtils.isEmpty(videoRequest.getPreDescription())) {
			updatedVideo.setPreDescription(videoRequest.getPreDescription());
		}
		if (videoRequest.getVideo() != null) {
			updatedVideo.setSource(fileUtil.uploadVideo(videoRequest.getVideo(), "course-video"));
		}
		return videoMapper.toDto(saveVideo(updatedVideo));
	}

	@Override
	public void deleteVideo(Integer videoId) {
		VideoEntity deletedVideo = findVideoEntityById(videoId);
		if (!contextUtil.isAdminRole() && !isRequestByCourseInstructor(deletedVideo.getCourse())) {
			UserEntity user = contextUtil.loadUserFromContext();
			System.err.println(user);
			System.err.println(deletedVideo.getCourse().getInstructor().getEmail());
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		CourseEntity course = deletedVideo.getCourse();
		course.removeVideo(videoId);
		courseService.saveCourse(course);
		videoRepository.delete(deletedVideo);
	}

	@Override
	public VideoEntity findVideoEntityById(Integer videoId) {
		Optional<VideoEntity> video = videoRepository.findById(videoId);
		if (video.isEmpty()) {
			throw new NotFoundException(
					messageSource.getMessage("msg.not-found", new Object[]{"Video id", videoId}, Locale.ENGLISH));
		}
		return video.get();
	}

	private boolean isRequestByCourseInstructor(CourseEntity course) {
		return Objects.equals(course.getInstructor().getId(), contextUtil.loadUserFromContext().getId());
	}
}

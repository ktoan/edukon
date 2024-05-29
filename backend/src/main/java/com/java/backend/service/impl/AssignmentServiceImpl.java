package com.java.backend.service.impl;

import com.java.backend.dto.AssignmentDto;
import com.java.backend.entity.AssignmentEntity;
import com.java.backend.entity.VideoEntity;
import com.java.backend.exception.BadRequestException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.AssignmentMapper;
import com.java.backend.repository.AssignmentRepository;
import com.java.backend.request.AssignmentRequest;
import com.java.backend.service.AssignmentService;
import com.java.backend.service.VideoService;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
	private final AssignmentRepository assignmentRepository;
	private final AssignmentMapper assignmentMapper;
	private final VideoService videoService;
	private final MessageSource messageSource;
	private final FileUtil fileUtil;

	@Override
	public AssignmentEntity saveAssignment(AssignmentEntity assignment) {
		return assignmentRepository.save(assignment);
	}

	@Override
	public AssignmentDto createAssignment(AssignmentRequest assignmentRequest) {
		VideoEntity video = videoService.findVideoEntityById(assignmentRequest.getVideoId());
		if (video.getAssignment() != null) {
			throw new BadRequestException("Video has assignment!");
		}
		AssignmentEntity newAssignment = new AssignmentEntity();
		newAssignment.setTitle(assignmentRequest.getTitle());
		newAssignment.setContent(assignmentRequest.getContent());
		newAssignment.setRequirement(assignmentRequest.getRequirement());
		newAssignment.setSource(fileUtil.uploadVideo(assignmentRequest.getFile(), "references"));
		newAssignment.setVideo(video);
		return assignmentMapper.toDto(saveAssignment(newAssignment));
	}

	@Override
	public AssignmentEntity findAssignmentEntityById(Integer assignmentId) {
		Optional<AssignmentEntity> assignment = assignmentRepository.findById(assignmentId);
		if (assignment.isEmpty()) {
			throw new NotFoundException(
					messageSource.getMessage("msg.not-found", new Object[]{"Assignment id", assignmentId},
							Locale.ENGLISH));
		}
		return assignment.get();
	}
}

package com.java.backend.mapper;

import com.java.backend.dto.SubmissionDto;
import com.java.backend.entity.SubmissionEntity;

public interface SubmissionMapper {
	SubmissionDto toDto(SubmissionEntity submission);
}

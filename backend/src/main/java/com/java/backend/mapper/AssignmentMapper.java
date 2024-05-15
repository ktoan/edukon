package com.java.backend.mapper;

import com.java.backend.dto.AssignmentDto;
import com.java.backend.entity.AssignmentEntity;

public interface AssignmentMapper {
	AssignmentEntity toEntity(AssignmentDto assignmentDto);

	AssignmentDto toDto(AssignmentEntity assignment);
}

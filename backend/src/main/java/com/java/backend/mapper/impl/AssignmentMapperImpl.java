package com.java.backend.mapper.impl;

import com.java.backend.dto.AssignmentDto;
import com.java.backend.entity.AssignmentEntity;
import com.java.backend.mapper.AssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentMapperImpl implements AssignmentMapper {
	private final ModelMapper modelMapper;

	@Override
	public AssignmentEntity toEntity(AssignmentDto assignmentDto) {
		return modelMapper.map(assignmentDto, AssignmentEntity.class);
	}

	@Override
	public AssignmentDto toDto(AssignmentEntity assignment) {
		return modelMapper.map(assignment, AssignmentDto.class);
	}
}

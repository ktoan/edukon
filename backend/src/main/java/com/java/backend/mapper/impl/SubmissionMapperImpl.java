package com.java.backend.mapper.impl;

import com.java.backend.dto.SubmissionDto;
import com.java.backend.entity.SubmissionEntity;
import com.java.backend.mapper.SubmissionMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubmissionMapperImpl implements SubmissionMapper {
    private final ModelMapper modelMapper;
    
    @Override
    public SubmissionDto toDto(SubmissionEntity submission) {
        return modelMapper.map(submission, SubmissionDto.class);
    }
}

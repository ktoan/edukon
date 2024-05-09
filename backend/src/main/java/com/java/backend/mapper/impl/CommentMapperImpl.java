package com.java.backend.mapper.impl;

import com.java.backend.dto.CommentDto;
import com.java.backend.entity.CommentEntity;
import com.java.backend.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper {
    private final ModelMapper modelMapper;

    @Override
    public CommentEntity toEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, CommentEntity.class);
    }

    @Override
    public CommentDto toDto(CommentEntity comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
}

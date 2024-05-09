package com.java.backend.mapper;

import com.java.backend.dto.CommentDto;
import com.java.backend.entity.CommentEntity;

public interface CommentMapper {
    CommentEntity toEntity(CommentDto commentDto);

    CommentDto toDto(CommentEntity comment);
}

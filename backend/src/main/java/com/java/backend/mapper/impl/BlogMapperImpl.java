package com.java.backend.mapper.impl;

import com.java.backend.dto.BlogDto;
import com.java.backend.entity.BlogEntity;
import com.java.backend.mapper.BlogMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogMapperImpl implements BlogMapper {
    private final ModelMapper modelMapper;

    @Override
    public BlogEntity toEntity(BlogDto blogDto) {
        return modelMapper.map(blogDto, BlogEntity.class);
    }

    @Override
    public BlogDto toDto(BlogEntity blog) {
        return modelMapper.map(blog, BlogDto.class);
    }
}

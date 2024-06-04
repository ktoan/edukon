package com.java.backend.mapper;

import com.java.backend.dto.BlogDto;
import com.java.backend.entity.BlogEntity;

public interface BlogMapper {
	BlogEntity toEntity(BlogDto blogDto);

	BlogDto toDto(BlogEntity blog);
}

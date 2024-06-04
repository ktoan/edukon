package com.java.backend.mapper;

import com.java.backend.dto.UserDto;
import com.java.backend.entity.UserEntity;

public interface UserMapper {
	UserEntity toEntity(UserDto userDto);

	UserDto toDto(UserEntity user);
}

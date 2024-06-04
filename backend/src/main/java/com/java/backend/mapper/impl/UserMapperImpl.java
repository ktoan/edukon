package com.java.backend.mapper.impl;

import com.java.backend.dto.UserDto;
import com.java.backend.entity.UserEntity;
import com.java.backend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
	private final ModelMapper modelMapper;

	@Override
	public UserEntity toEntity(UserDto userDto) {
		return modelMapper.map(userDto, UserEntity.class);
	}

	@Override
	public UserDto toDto(UserEntity user) {
		return modelMapper.map(user, UserDto.class);
	}
}

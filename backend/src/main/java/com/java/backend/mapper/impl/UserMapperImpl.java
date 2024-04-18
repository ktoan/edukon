package com.java.backend.mapper.impl;

import com.java.backend.dto.UserDto;
import com.java.backend.entity.UserEntity;
import com.java.backend.mapper.IUserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class UserMapperImpl implements IUserMapper {
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

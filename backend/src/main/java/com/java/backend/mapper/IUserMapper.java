package com.java.backend.mapper;

import com.java.backend.dto.UserDto;
import com.java.backend.entity.UserEntity;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
public interface IUserMapper {
    UserEntity toEntity(UserDto userDto);
    UserDto toDto(UserEntity user);
}

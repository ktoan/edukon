package com.java.backend.service;

import com.java.backend.dto.UserDto;
import com.java.backend.entity.UserEntity;
import com.java.backend.request.ConfirmAccountRequest;
import com.java.backend.request.LoginRequest;
import com.java.backend.request.RegisterRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserEntity saveUser(UserEntity user);

    UserDto loadUser();

    UserDto findUserByEmail(String email);

    LoginRequest validateLogin(LoginRequest loginRequest);

    void register(RegisterRequest registerRequest);

    void confirmToken(ConfirmAccountRequest confirmAccountRequest);

    void requestToken(String email);
    UserDto updateUser(UserDto userDto);
    String uploadAvatar(MultipartFile file);
}

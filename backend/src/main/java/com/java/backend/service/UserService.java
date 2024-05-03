package com.java.backend.service;

import com.java.backend.dto.UserDto;
import com.java.backend.entity.UserEntity;
import com.java.backend.request.LoginRequest;
import com.java.backend.request.RegisterRequest;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
public interface UserService {
    UserEntity saveUser(UserEntity user);
    UserDto loadUser();
    UserDto findUserByEmail(String email);
    LoginRequest validateLogin(LoginRequest loginRequest);
    void register(RegisterRequest registerRequest);
    void confirmToken(String token);
    void requestToken(String email);
}

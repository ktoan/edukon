package com.java.backend.service.impl;

import com.java.backend.constant.ExceptionMessage;
import com.java.backend.dto.UserDto;
import com.java.backend.entity.UserEntity;
import com.java.backend.enums.Gender;
import com.java.backend.enums.Role;
import com.java.backend.exception.BadRequestException;
import com.java.backend.exception.InternalServerException;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.UserMapper;
import com.java.backend.repository.UserRepository;
import com.java.backend.request.LoginRequest;
import com.java.backend.request.RegisterRequest;
import com.java.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Value("${app.default.avatar}")
    private String defaultAvatar;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto loadUser() {
        return null;
    }

    @Override
    public LoginRequest validateLogin(LoginRequest loginRequest) {
        Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            throw new BadRequestException(ExceptionMessage.LOGIN_FAILED);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new BadRequestException(ExceptionMessage.LOGIN_FAILED);
        }

        if (user.get().isLocked()) {
            throw new NotAccessException(ExceptionMessage.USER_BLOCKED);
        }

        if (!user.get().isEnabled()) {
            throw new NotAccessException(ExceptionMessage.USER_UNABLE);
        }

        return loginRequest;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        try {
            boolean isExistedMail = userRepository.existsByEmail(registerRequest.getEmail());

            if (isExistedMail) {
                throw new BadRequestException(String.format(ExceptionMessage.EXISTS, "Email", registerRequest.getEmail()));
            }

            UserEntity user = new UserEntity();

            user.setName(registerRequest.getName());
            user.setEmail(registerRequest.getEmail());
            user.setRole(Role.STUDENT);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setGender(Gender.valueOf(registerRequest.getGender()));
            user.setAvatar(defaultAvatar);

            saveUser(user);
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Override
    public void confirmToken(String token) {

    }

    @Override
    public void requestToken(String email) {

    }

    @Override
    public UserDto findUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessage.NOT_FOUND, "Email", email));
        }

        return userMapper.toDto(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Email isn't found");
        }
    }
}

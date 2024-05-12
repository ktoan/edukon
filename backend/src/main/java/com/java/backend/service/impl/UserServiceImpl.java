package com.java.backend.service.impl;

import com.java.backend.dto.UserDto;
import com.java.backend.entity.ConfirmationTokenEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.enums.Gender;
import com.java.backend.enums.Role;
import com.java.backend.exception.BadRequestException;
import com.java.backend.exception.InternalServerException;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.UserMapper;
import com.java.backend.repository.UserRepository;
import com.java.backend.request.ConfirmAccountRequest;
import com.java.backend.request.LoginRequest;
import com.java.backend.request.RegisterRequest;
import com.java.backend.service.ConfirmationTokenService;
import com.java.backend.service.UserService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.MailUtil;
import com.java.backend.util.StringUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	@Value("${app.default.avatar}")
	private String defaultAvatar;

	private final UserRepository userRepository;
	private final ConfirmationTokenService confirmationTokenService;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final MailUtil mailUtil;
	private final StringUtil stringUtil;
	private final MessageSource messageSource;
	private final ContextUtil contextUtil;

	@Override
	public UserEntity saveUser(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public UserDto loadUser() {
		return userMapper.toDto(contextUtil.loadUserFromContext());
	}

	@Override
	public LoginRequest validateLogin(LoginRequest loginRequest) {
		String loginFailedMsg = messageSource.getMessage("msg.login-failed", null, Locale.ENGLISH);
		String notAccessMsg = messageSource.getMessage("msg.not-access", null, Locale.ENGLISH);
		Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getEmail());
		if (user.isEmpty()) {
			throw new BadRequestException(loginFailedMsg);
		}
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
			throw new BadRequestException(loginFailedMsg);
		}
		if (user.get().isLocked()) {
			throw new NotAccessException(notAccessMsg);
		}
		if (!user.get().isEnabled()) {
			throw new NotAccessException(notAccessMsg);
		}
		return loginRequest;
	}

	@Override
	public void register(RegisterRequest registerRequest) {
		try {
			boolean isExistedMail = userRepository.existsByEmail(registerRequest.getEmail());
			if (isExistedMail) {
				throw new BadRequestException(messageSource.getMessage("msg.already-exists",
						new Object[]{"Email", registerRequest.getEmail()}, Locale.ENGLISH));
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
	public void confirmToken(ConfirmAccountRequest confirmAccountRequest) {
		UserEntity user = findUserEntityByEmail(confirmAccountRequest.getEmail());
		ConfirmationTokenEntity confirmationToken = confirmationTokenService.findByTokenValue(
				confirmAccountRequest.getToken());
		if (confirmationTokenService.isValidToken(user, confirmationToken)) {
			user.setActivated(true);
			saveUser(user);
		} else {
			throw new BadRequestException("Confirmation token is invalid!");
		}
	}

	@Override
	public void requestToken(String email) {
		try {
			UserEntity user = findUserEntityByEmail(email);
			String token = stringUtil.generateConfirmationToken();
			ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity();
			confirmationToken.setUser(user);
			confirmationToken.setToken(token);
			confirmationToken.setExpiredAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
			confirmationTokenService.saveToken(confirmationToken);
			mailUtil.sendTokenMail(email, "[EduKon] Send token to verify account", user.getName(), token);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserDto findUserByEmail(String email) {
		return userMapper.toDto(findUserEntityByEmail(email));
	}

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		return findUserEntityByEmail(email);
	}

	private UserEntity findUserEntityByEmail(String email) {
		Optional<UserEntity> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			throw new NotFoundException(
					messageSource.getMessage("msg.not-found", new Object[]{"Email", email}, Locale.ENGLISH));
		}
		return user.get();
	}
}

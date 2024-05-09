package com.java.backend.service.impl;

import com.java.backend.entity.ConfirmationTokenEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.exception.NotFoundException;
import com.java.backend.repository.ConfirmationTokenRepository;
import com.java.backend.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
	private final ConfirmationTokenRepository confirmationTokenRepository;
	private final MessageSource messageSource;

	@Override
	public ConfirmationTokenEntity findByTokenValue(String token) {
		return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException(
				messageSource.getMessage("msg.not-found", new Object[]{"Token", token}, Locale.ENGLISH)));
	}

	@Override
	public void saveToken(ConfirmationTokenEntity confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}

	@Override
	public boolean isValidToken(UserEntity user, ConfirmationTokenEntity token) {
		List<ConfirmationTokenEntity> confirmationTokens = confirmationTokenRepository.findByUserAndExpiredAtAfter(user,
				new Date());
		return !confirmationTokens.isEmpty();
	}
}

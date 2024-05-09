package com.java.backend.service;

import com.java.backend.entity.ConfirmationTokenEntity;
import com.java.backend.entity.UserEntity;

public interface ConfirmationTokenService {
    ConfirmationTokenEntity findByTokenValue(String token);

    void saveToken(ConfirmationTokenEntity confirmationToken);

    boolean isValidToken(UserEntity user, ConfirmationTokenEntity token);
}

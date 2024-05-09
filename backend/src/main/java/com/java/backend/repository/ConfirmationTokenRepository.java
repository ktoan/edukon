package com.java.backend.repository;

import com.java.backend.entity.ConfirmationTokenEntity;
import com.java.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Integer> {
    Optional<ConfirmationTokenEntity> findByToken(String token);
    List<ConfirmationTokenEntity> findByUserAndExpiredAtAfter(UserEntity user, Date date);
}

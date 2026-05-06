package com.tesis.authserver.service;

import com.tesis.authserver.entity.AppUser;
import com.tesis.authserver.entity.GoogleUser;
import com.tesis.authserver.entity.RefreshToken;
import com.tesis.authserver.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshToken createRefreshToken(AppUser user) {
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS)) // 🔥 duración
                .build();

        return repository.save(token);
    }

    public RefreshToken createRefreshTokenGoogle(GoogleUser user) {
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .googleUser(user)
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();

        return repository.save(token);
    }

    public RefreshToken verifyToken(String token) {
        RefreshToken rt = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (rt.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(rt);
            throw new RuntimeException("Refresh token expired");
        }

        return rt;
    }
}

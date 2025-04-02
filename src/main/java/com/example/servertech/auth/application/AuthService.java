package com.example.servertech.auth.application;

import com.example.servertech.auth.jwt.JwtProvider;
import com.example.servertech.domain.user.entity.UserRole;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtProvider jwtProvider;

	public TokenResponse createToken(Long id, UserRole role) {
		String accessToken = jwtProvider.generateAccessToken(id, role);
		return TokenResponse.of(accessToken);
	}
}

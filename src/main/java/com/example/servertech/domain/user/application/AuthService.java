package com.example.servertech.domain.user.application;

import com.example.servertech.domain.user.entity.UserRole;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
	// todo
	public TokenResponse createToken(Long id, UserRole role) {
		return null;
	}
}

package com.example.servertech.domain.user.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.user.exception.UserExceptionCode.AUTHORIZATION;

public class AuthorizationException extends CustomException {
	public AuthorizationException() {
		super(AUTHORIZATION);
	}
}

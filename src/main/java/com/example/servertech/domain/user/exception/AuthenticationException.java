package com.example.servertech.domain.user.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.user.exception.UserExceptionCode.AUTHENTICATION;

public class AuthenticationException extends CustomException {
	public AuthenticationException() {
		super(AUTHENTICATION);
	}
}

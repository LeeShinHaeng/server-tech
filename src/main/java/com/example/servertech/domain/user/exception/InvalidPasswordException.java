package com.example.servertech.domain.user.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.user.exception.UserExceptionCode.INVALID_PASSWORD;

public class InvalidPasswordException extends CustomException {
	public InvalidPasswordException() {
		super(INVALID_PASSWORD);
	}
}

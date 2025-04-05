package com.example.servertech.auth.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.auth.exception.AuthExceptionCode.UNAUTHORIZED_USER;

public class UnauthorizedUserException extends CustomException {
	public UnauthorizedUserException() {
		super(UNAUTHORIZED_USER);
	}
}

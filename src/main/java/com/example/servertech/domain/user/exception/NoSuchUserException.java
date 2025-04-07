package com.example.servertech.domain.user.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.user.exception.UserExceptionCode.NO_SUCH_USER;

public class NoSuchUserException extends CustomException {
	public NoSuchUserException() {
		super(NO_SUCH_USER);
	}
}

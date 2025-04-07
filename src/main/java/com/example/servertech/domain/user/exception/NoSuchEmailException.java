package com.example.servertech.domain.user.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.user.exception.UserExceptionCode.NO_SUCH_USER_EMAIL;

public class NoSuchEmailException extends CustomException {
	public NoSuchEmailException() {
		super(NO_SUCH_USER_EMAIL);
	}
}

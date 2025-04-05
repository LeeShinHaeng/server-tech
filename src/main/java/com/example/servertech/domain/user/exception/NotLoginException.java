package com.example.servertech.domain.user.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.user.exception.UserExceptionCode.NOT_LOGIN;

public class NotLoginException extends CustomException {
	public NotLoginException() {
		super(NOT_LOGIN);
	}
}

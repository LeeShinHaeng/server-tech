package com.example.servertech.auth.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.auth.exception.AuthExceptionCode.INVALID_JWT;

public class InvalidJwtException extends CustomException {
	public InvalidJwtException() {
		super(INVALID_JWT);
	}
}

package com.example.servertech.auth.exception;

import com.example.servertech.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {
	INVALID_JWT(BAD_REQUEST, "JWT가 유효하지 않습니다"),
	UNAUTHORIZED_USER(UNAUTHORIZED, "유효한 역할의 유저가 아닙니다"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}

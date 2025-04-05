package com.example.servertech.domain.user.exception;

import com.example.servertech.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
	NO_SUCH_EMAIL(BAD_REQUEST, "해당 이메일의 사용자가 존재하지 않습니다"),
	INVALID_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
	NOT_LOGIN(BAD_REQUEST, "로그인이 확인되지 않습니다"),
	AUTHORIZATION(BAD_REQUEST, "인증과정 중 에러가 발생했습니다"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}

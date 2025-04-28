package com.example.servertech.domain.common.entity.exception;

import com.example.servertech.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum CommonExceptionCode implements ExceptionCode {
	LOCK_INTERRUPTED_EXCEPTION(INTERNAL_SERVER_ERROR, "락 획득 중 인터럽트가 발생했습니다"),
	TRY_LOCK_FAILURE(CONFLICT, "다른 사용자가 선점한 행위입니다"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}

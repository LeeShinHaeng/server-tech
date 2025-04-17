package com.example.servertech.common.event.exception;

import com.example.servertech.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum EventExceptionCode implements ExceptionCode {
	STREAM_PROCESS(INTERNAL_SERVER_ERROR, "스트림 처리 중 에러가 발생했습니다"),
	INVALID_MESSAGE_FORMAT(BAD_REQUEST, "메시지 형식이 올바르지 않습니다"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}

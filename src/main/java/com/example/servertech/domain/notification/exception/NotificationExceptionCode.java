package com.example.servertech.domain.notification.exception;

import com.example.servertech.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum NotificationExceptionCode implements ExceptionCode {
	NO_SUCH_NOTIFICATION(BAD_REQUEST, "해당 아이디의 알림이 존재하지 않습니다"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}

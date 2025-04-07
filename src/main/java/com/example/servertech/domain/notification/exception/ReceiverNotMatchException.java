package com.example.servertech.domain.notification.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.notification.exception.NotificationExceptionCode.RECEIVER_NOT_MATCH;

public class ReceiverNotMatchException extends CustomException {
	public ReceiverNotMatchException() {
		super(RECEIVER_NOT_MATCH);
	}
}

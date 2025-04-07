package com.example.servertech.domain.notification.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.notification.exception.NotificationExceptionCode.NO_SUCH_NOTIFICATION;

public class NoSuchNotificationTypeException extends CustomException {
	public NoSuchNotificationTypeException() {
		super(NO_SUCH_NOTIFICATION);
	}
}

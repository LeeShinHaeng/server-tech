package com.example.servertech.common.event.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.common.event.exception.EventExceptionCode.INVALID_MESSAGE_FORMAT;

public class InvalidMessageFormatException extends CustomException {
	public InvalidMessageFormatException() {
		super(INVALID_MESSAGE_FORMAT);
	}
}

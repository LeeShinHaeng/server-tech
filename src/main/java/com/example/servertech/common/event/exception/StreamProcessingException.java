package com.example.servertech.common.event.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.common.event.exception.EventExceptionCode.STREAM_PROCESS;

public class StreamProcessingException extends CustomException {
	public StreamProcessingException() {
		super(STREAM_PROCESS);
	}
}

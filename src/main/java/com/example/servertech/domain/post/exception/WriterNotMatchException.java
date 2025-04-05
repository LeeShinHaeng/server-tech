package com.example.servertech.domain.post.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.post.exception.PostExceptionCode.WRITER_NOT_MATCH;

public class WriterNotMatchException extends CustomException {
	public WriterNotMatchException() {
		super(WRITER_NOT_MATCH);
	}
}

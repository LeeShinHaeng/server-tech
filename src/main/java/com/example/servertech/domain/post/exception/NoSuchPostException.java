package com.example.servertech.domain.post.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.post.exception.PostExceptionCode.NO_SUCH_POST;

public class NoSuchPostException extends CustomException {
	public NoSuchPostException() {
		super(NO_SUCH_POST);
	}
}

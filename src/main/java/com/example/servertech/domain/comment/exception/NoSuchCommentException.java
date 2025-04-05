package com.example.servertech.domain.comment.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.comment.exception.CommentExceptionCode.NO_SUCH_COMMENT;

public class NoSuchCommentException extends CustomException {
	public NoSuchCommentException() {
		super(NO_SUCH_COMMENT);
	}
}

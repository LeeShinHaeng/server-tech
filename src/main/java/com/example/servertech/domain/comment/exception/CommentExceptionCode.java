package com.example.servertech.domain.comment.exception;

import com.example.servertech.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum CommentExceptionCode implements ExceptionCode {
	WRITER_NOT_MATCH(UNAUTHORIZED, "작성자와 로그인한 유저가 일치하지 않습니다"),
	NO_SUCH_COMMENT(BAD_REQUEST, "해당 아이디의 댓글이 존재하지 않습니다"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}

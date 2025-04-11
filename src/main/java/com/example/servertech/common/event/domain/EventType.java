package com.example.servertech.common.event.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
	POST_COMMENTED("게시글에 댓글이 달렸습니다."),
	POST_LIKE("게시글에 사용자가 좋아요를 눌렀습니다."),
	COMMENT_LIKE("댓글에 사용자가 좋아요를 눌렀습니다."),
	;

	private final String description;
}

package com.example.servertech.domain.comment.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CommentCreateRequest(
	@Schema(description = "댓글 내용", example = "어서오세요!", requiredMode = REQUIRED)
	String content
) {
}

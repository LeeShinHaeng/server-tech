package com.example.servertech.domain.comment.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record CommentPersistResponse(

	@Schema(description = "댓글 아이디", example = "1", requiredMode = REQUIRED)
	Long id
) {
	public static CommentPersistResponse create(Long id) {
		return CommentPersistResponse.builder()
			.id(id)
			.build();
	}
}

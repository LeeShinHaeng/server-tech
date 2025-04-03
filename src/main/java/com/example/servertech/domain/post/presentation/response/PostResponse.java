package com.example.servertech.domain.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record PostResponse(
	@Schema(description = "게시글 아이디", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "게시글 제목", example = "가입 인사", requiredMode = REQUIRED)
	String title
) {
	public static PostResponse create(Long id, String title) {
		return PostResponse.builder()
			.id(id)
			.title(title)
			.build();
	}
}

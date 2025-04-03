package com.example.servertech.domain.post.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record PostCreateRequest(
	@Schema(description = "제목", example = "신입 인사", requiredMode = REQUIRED)
	String title,

	@Schema(description = "내용", example = "잘부탁드립니다!", requiredMode = REQUIRED)
	String content
) {
}

package com.example.servertech.domain.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record PostPersistResponse(
	@Schema(description = "생성된 아이디", example = "1", requiredMode = REQUIRED)
	Long id
) {
	public static PostPersistResponse create(Long id) {
		return PostPersistResponse.builder()
			.id(id)
			.build();
	}
}

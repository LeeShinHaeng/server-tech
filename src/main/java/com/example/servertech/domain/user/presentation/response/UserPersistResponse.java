package com.example.servertech.domain.user.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record UserPersistResponse(
	@Schema(description = "생성된 아이디", example = "1", requiredMode = REQUIRED)
	Long id
) {
	public static UserPersistResponse of(Long id) {
		return UserPersistResponse.builder()
			.id(id)
			.build();
	}
}

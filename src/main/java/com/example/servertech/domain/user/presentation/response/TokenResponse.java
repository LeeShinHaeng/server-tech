package com.example.servertech.domain.user.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record TokenResponse(
	@Schema(description = "생성된 토큰", example = "", requiredMode = REQUIRED)
	String accessToken
) {
	public static TokenResponse of(String accessToken) {
		return TokenResponse.builder()
			.accessToken(accessToken)
			.build();
	}
}

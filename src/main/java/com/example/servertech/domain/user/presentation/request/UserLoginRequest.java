package com.example.servertech.domain.user.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UserLoginRequest(
	@Schema(description = "이메일", example = "user@email.com", requiredMode = REQUIRED)
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "유효한 이메일 형식이 아닙니다.")
	String email,

	@Schema(description = "비밀번호", example = "q1w2e3r4!", requiredMode = REQUIRED)
	String password
) {
}

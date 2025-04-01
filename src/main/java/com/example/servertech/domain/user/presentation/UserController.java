package com.example.servertech.domain.user.presentation;

import com.example.servertech.domain.user.presentation.request.CreateUserRequest;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "회원 API")
public interface UserController {
	@Operation(summary = "회원 가입 API", description = """
			- Description : 이 API는 새로운 회원을 생성 합니다.
		""")
	@ApiResponse(responseCode = "201")
	ResponseEntity<UserPersistResponse> register(CreateUserRequest request);
}

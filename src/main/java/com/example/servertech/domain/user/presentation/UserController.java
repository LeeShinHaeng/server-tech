package com.example.servertech.domain.user.presentation;

import com.example.servertech.domain.user.presentation.request.UserCreateRequest;
import com.example.servertech.domain.user.presentation.request.UserLoginRequest;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
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
	ResponseEntity<UserPersistResponse> register(UserCreateRequest request);

	@Operation(summary = "로그인 API", description = """
			- Description : 이 API는 로그인합니다.
		""")
	@ApiResponse(responseCode = "200")
	ResponseEntity<TokenResponse> login(UserLoginRequest request);

	@Operation(summary = "정보 수정 API", description = """
			- Description : 이 API는 회원 정보를 수정합니다.
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> update(UserCreateRequest request);

	@Operation(summary = "탈퇴 API", description = """
			- Description : 이 API는 회원 탈퇴합니다.
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete();
}

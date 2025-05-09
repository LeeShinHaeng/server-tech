package com.example.servertech.domain.user.presentation;

import com.example.servertech.domain.user.presentation.request.UserCreateRequest;
import com.example.servertech.domain.user.presentation.request.UserLoginRequest;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
import com.example.servertech.domain.user.presentation.response.UserDetailResponse;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "회원 API")
public interface UserController {
	@Operation(summary = "회원 가입 API", description = """
			- Description : 이 API는 새로운 회원을 생성 합니다.
		""")
	@ApiResponse(
		responseCode = "201",
		content = @Content(schema = @Schema(implementation = UserPersistResponse.class))
	)
	ResponseEntity<UserPersistResponse> register(UserCreateRequest request);

	@Operation(summary = "로그인 API", description = """
			- Description : 이 API는 로그인합니다.
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = TokenResponse.class))
	)
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

	@Operation(summary = "마이페이지 API", description = """
			- Description : 이 API는 마이페이지를 조회합니다.
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = UserDetailResponse.class))
	)
	ResponseEntity<UserDetailResponse> me();

	@Operation(summary = "관리자 회원 가입 API", description = """
			- Description : 이 API는 새로운 관리자를 생성 합니다.
		""")
	@ApiResponse(
		responseCode = "201",
		content = @Content(schema = @Schema(implementation = UserPersistResponse.class))
	)
	ResponseEntity<UserPersistResponse> adminRegister(UserCreateRequest request);

	@Operation(summary = "강제 탈퇴 API", description = """
			- Description : 이 API는 관리자 전용으로 회원을 탈퇴시킵니다.
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> blockUser(Long id);
}

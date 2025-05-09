package com.example.servertech.domain.user.presentation;

import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.presentation.request.UserCreateRequest;
import com.example.servertech.domain.user.presentation.request.UserLoginRequest;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
import com.example.servertech.domain.user.presentation.response.UserDetailResponse;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {
	private final UserService userService;

	@Override
	@PostMapping("/register")
	public ResponseEntity<UserPersistResponse> register(@RequestBody UserCreateRequest request) {
		UserPersistResponse response = userService.register(request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request) {
		TokenResponse response = userService.login(request);
		return ResponseEntity.ok(response);
	}

	@Override
	@PatchMapping("/update")
	public ResponseEntity<Void> update(@RequestBody UserCreateRequest request) {
		userService.update(request);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping
	public ResponseEntity<Void> delete() {
		userService.delete();
		return ResponseEntity.noContent().build();
	}

	@Override
	@GetMapping("/me")
	public ResponseEntity<UserDetailResponse> me() {
		UserDetailResponse response = userService.mypage();
		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping("/admin-register")
	public ResponseEntity<UserPersistResponse> adminRegister(UserCreateRequest request) {
		UserPersistResponse response = userService.adminRegister(request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@Override
	@PatchMapping("/block/{id}")
	public ResponseEntity<Void> blockUser(@PathVariable Long id) {
		userService.blockUser(id);
		return ResponseEntity.noContent().build();
	}
}

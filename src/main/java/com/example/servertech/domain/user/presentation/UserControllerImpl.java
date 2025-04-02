package com.example.servertech.domain.user.presentation;

import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.presentation.request.UserCreateRequest;
import com.example.servertech.domain.user.presentation.request.UserLoginRequest;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<UserPersistResponse> register(UserCreateRequest request) {
		UserPersistResponse response = userService.register(request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(UserLoginRequest request) {
		TokenResponse response = userService.login(request);
		return ResponseEntity.ok(response);
	}

	@Override
	@PatchMapping("/update")
	public ResponseEntity<Void> update(UserCreateRequest request) {
		userService.update(request);
		return ResponseEntity.noContent().build();
	}
}

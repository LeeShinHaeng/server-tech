package com.example.servertech.domain.user.presentation;

import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.presentation.request.CreateUserRequest;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
	private final UserService userService;

	@Override
	@PostMapping("/register")
	public ResponseEntity<UserPersistResponse> register(CreateUserRequest request) {
		UserPersistResponse response = userService.register(request);
		return ResponseEntity.status(CREATED).body(response);
	}
}

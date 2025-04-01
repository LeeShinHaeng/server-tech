package com.example.servertech.domain.user.presentation;

import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.presentation.request.CreateUserRequest;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
	private final UserService userService;

	@Override
	public ResponseEntity<UserPersistResponse> register(CreateUserRequest request) {
		UserPersistResponse response = userService.register(request);
		return ResponseEntity.status(CREATED).body(response);
	}
}

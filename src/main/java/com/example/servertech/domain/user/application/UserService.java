package com.example.servertech.domain.user.application;

import com.example.servertech.domain.user.entity.User;
import com.example.servertech.domain.user.presentation.request.CreateUserRequest;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import com.example.servertech.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserPersistResponse register(CreateUserRequest request) {
		String encode = passwordEncoder.encode(request.password());
		User user = User.createNormal(request.name(), request.email(), encode);
		User save = userRepository.save(user);
		return UserPersistResponse.of(save.getId());
	}
}

package com.example.servertech.domain.user.application;

import com.example.servertech.auth.application.AuthService;
import com.example.servertech.domain.user.entity.User;
import com.example.servertech.domain.user.presentation.request.UserCreateRequest;
import com.example.servertech.domain.user.presentation.request.UserLoginRequest;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
import com.example.servertech.domain.user.presentation.response.UserDetailResponse;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import com.example.servertech.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final AuthService authService;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public UserPersistResponse register(UserCreateRequest request) {
		String encode = passwordEncoder.encode(request.password());
		User user = User.createNormal(request.name(), request.email(), encode);
		User save = userRepository.save(user);
		return UserPersistResponse.of(save.getId());
	}

	@Transactional(readOnly = true)
	public TokenResponse login(UserLoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new RuntimeException("로그인에 실패헸습니다"));
		if (!user.isPasswordMatch(request.password(), passwordEncoder)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다");
		}

		return authService.createToken(user.getId(), user.getRole());
	}

	@Transactional
	public void update(UserCreateRequest request) {
		User me = me();
		me.updateEmail(request.email());
		String encode = passwordEncoder.encode(request.password());
		me.updatePassword(encode);
		me.updateName(request.name());
	}

	@Transactional
	public void delete() {
		User me = me();
		me.delete();
	}

	@Transactional(readOnly = true)
	public UserDetailResponse mypage() {
		return UserDetailResponse.create(me());
	}

	@Transactional(readOnly = true)
	public User me() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null
			|| authentication.getPrincipal() == null
			|| !(authentication.getPrincipal() instanceof UserDetails)) {
			throw new RuntimeException("로그인이 확인되지 않습니다");
		}

		String username = ((UserDetails) authentication.getPrincipal()).getUsername();

		return userRepository.findById(Long.parseLong(username))
			.orElseThrow(() -> new RuntimeException("로그인이 확인되지 않습니다"));
	}
}

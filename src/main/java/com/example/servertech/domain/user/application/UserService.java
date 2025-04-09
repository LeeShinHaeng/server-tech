package com.example.servertech.domain.user.application;

import com.example.servertech.auth.application.AuthService;
import com.example.servertech.domain.user.entity.User;
import com.example.servertech.domain.user.exception.AuthorizationException;
import com.example.servertech.domain.user.exception.InvalidPasswordException;
import com.example.servertech.domain.user.exception.NoSuchEmailException;
import com.example.servertech.domain.user.exception.NoSuchUserException;
import com.example.servertech.domain.user.exception.NotLoginException;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final AuthService authService;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public UserPersistResponse register(UserCreateRequest request) {
		String encode = passwordEncoder.encode(request.password());
		User save = userRepository.save(User.createNormal(request.name(), request.email(), encode));
		return UserPersistResponse.of(save.getId());
	}

	@Transactional(readOnly = true)
	public TokenResponse login(UserLoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(NoSuchEmailException::new);
		if (!user.isPasswordMatch(request.password(), passwordEncoder)) {
			throw new InvalidPasswordException();
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
			throw new NotLoginException();
		}

		String username = ((UserDetails) authentication.getPrincipal()).getUsername();

		return userRepository.findById(Long.parseLong(username))
			.orElseThrow(AuthorizationException::new);
	}

	@Transactional(readOnly = true)
	public Optional<User> getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			return Optional.of(me());
		}
		return Optional.empty();
	}

	@Transactional(readOnly = true)
	public User getUserById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(NoSuchUserException::new);
	}

	@Transactional
	public UserPersistResponse adminRegister(UserCreateRequest request) {
		String encode = passwordEncoder.encode(request.password());
		User save = userRepository.save(User.createAdmin(request.name(), request.email(), encode));
		return UserPersistResponse.of(save.getId());
	}
}

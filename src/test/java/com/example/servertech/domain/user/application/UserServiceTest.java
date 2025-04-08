package com.example.servertech.domain.user.application;

import com.example.servertech.auth.application.AuthService;
import com.example.servertech.auth.jwt.JwtProperties;
import com.example.servertech.auth.jwt.JwtProvider;
import com.example.servertech.domain.user.entity.User;
import com.example.servertech.domain.user.exception.InvalidPasswordException;
import com.example.servertech.domain.user.exception.NoSuchEmailException;
import com.example.servertech.domain.user.exception.NotLoginException;
import com.example.servertech.domain.user.presentation.request.UserCreateRequest;
import com.example.servertech.domain.user.presentation.request.UserLoginRequest;
import com.example.servertech.domain.user.presentation.response.TokenResponse;
import com.example.servertech.domain.user.presentation.response.UserDetailResponse;
import com.example.servertech.domain.user.presentation.response.UserPersistResponse;
import com.example.servertech.domain.user.repository.UserRepository;
import com.example.servertech.mock.repository.FakeUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {
	private UserService userService;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private final String NAME = "이신행";
	private final String EMAIL = "user@example.com";
	private final String RAW_PASSWORD = "password";
	private User user;

	@BeforeEach
	void init() {
		UserRepository userRepository = new FakeUserRepository();
		JwtProperties jwtProperties = new JwtProperties("testIssuer", "testSecretKey");
		JwtProvider jwtProvider = new JwtProvider(jwtProperties);
		AuthService authService = new AuthService(jwtProvider);

		userService = new UserService(authService, userRepository, encoder);

		user = userRepository.save(
			User.builder()
				.id(1L)
				.name(NAME)
				.email(EMAIL)
				.password(encoder.encode(RAW_PASSWORD))
				.role(NORMAL)
				.build()
		);

		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);
	}

	@Test
	@DisplayName("register 는 새로운 User 를 저장한다.")
	void register_Success() {
		// given
		String EMAIL = "user2@example.com";
		UserCreateRequest userCreateRequest = UserCreateRequest.builder()
			.name(NAME)
			.email(EMAIL)
			.password(RAW_PASSWORD)
			.build();

		// when
		UserPersistResponse registered = userService.register(userCreateRequest);

		// then
		assertNotNull(registered);
		assertEquals(2L, registered.id());
	}

	@Test
	@DisplayName("login 은 정확하게 입력하면 AccessToken 을 생성한다.")
	void login_Success() {
		// given
		UserLoginRequest request = UserLoginRequest.builder()
			.email(EMAIL)
			.password(RAW_PASSWORD)
			.build();

		// when
		TokenResponse token = userService.login(request);

		// then
		assertNotNull(token);
	}

	@Test
	@DisplayName("login 은 존재하지 않는 이메일을 입력하면 NoSuchEmailException 을 반환한다.")
	void login_throw_NoSuchEmailException() {
		// given
		UserLoginRequest request = UserLoginRequest.builder()
			.email("NoSuchEmail")
			.password(RAW_PASSWORD)
			.build();

		// when
		// then
		Assertions.assertThatThrownBy(() -> userService.login(request))
			.isInstanceOf(NoSuchEmailException.class);
	}

	@Test
	@DisplayName("login 은 존재하지 않는 이메일을 입력하면 InvalidPasswordException 을 반환한다.")
	void login_throw_InvalidPasswordException() {
		// given
		UserLoginRequest request = UserLoginRequest.builder()
			.email(EMAIL)
			.password("NotValidPassword")
			.build();

		// when
		// then
		Assertions.assertThatThrownBy(() -> userService.login(request))
			.isInstanceOf(InvalidPasswordException.class);
	}

	@Test
	@DisplayName("update 는 User 의 정보를 변경한다.")
	void update_Success() {
		// given
		String NAME = "홍길동";
		String EMAIL = "user2@example.com";
		String RAW_PASSWORD = "new-password";
		UserCreateRequest request = UserCreateRequest.builder()
			.name(NAME)
			.email(EMAIL)
			.password(RAW_PASSWORD)
			.build();

		// when
		userService.update(request);

		// then
		assertNotEquals(this.NAME, user.getName());
		assertNotEquals(this.EMAIL, user.getEmail());
		assertFalse(encoder.matches(this.RAW_PASSWORD, user.getPassword()));
		assertEquals(NAME, user.getName());
		assertEquals(EMAIL, user.getEmail());
		assertTrue(encoder.matches(RAW_PASSWORD, user.getPassword()));
	}

	@Test
	@DisplayName("delete 는 User 의 deletedAt 을 현재로 변경한다.")
	void delete_Success() {
		// given
		// when
		userService.delete();
		User deleted = userService.getUserById(1L);

		// then
		assertNotNull(deleted.getDeletedAt());
	}

	@Test
	@DisplayName("mypage 는 현재 로그인 한 User 를 UserDetailResponse 로 반환한다.")
	void mypage_Success() {
		// when
		UserDetailResponse response = userService.mypage();

		// then
		assertEquals(user.getId(), response.id());
		assertEquals(user.getName(), response.name());
		assertEquals(user.getEmail(), response.email());
		assertEquals(user.getRole().getDescription(), response.role());
	}

	@Test
	@DisplayName("me 는 현재 로그인 한 User 를 반환한다.")
	void me_Success() {
		// when
		User me = userService.me();

		// then
		assertNotNull(me);
		assertEquals(NAME, me.getName());
		assertEquals(EMAIL, me.getEmail());
	}

	@Test
	@DisplayName("me 는 현재 로그인 한 User 가 없을 경우 NotLoginException 를 반환한다.")
	void me_throw_NotLoginException() {
		// given
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);

		// when
		// then
		Assertions.assertThatThrownBy(() -> userService.me())
			.isInstanceOf(NotLoginException.class);
	}

	@Test
	@DisplayName("getAuthenticatedUser 는 현재 로그인 한 User 를 Optional 로 반환한다.")
	void getAuthenticatedUser_Success() {
		// when
		Optional<User> optionalUser = userService.getAuthenticatedUser();

		// then
		assertTrue(optionalUser.isPresent());
		assertEquals(user.getId(), optionalUser.get().getId());
		assertEquals(user.getName(), optionalUser.get().getName());
		assertEquals(user.getEmail(), optionalUser.get().getEmail());
	}

	@Test
	@DisplayName("getAuthenticatedUser 는 현재 로그인 한 User 가 없을 경우 Null 로 반환한다.")
	void getAuthenticatedUser_Null() {
		// given
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);

		// when
		Optional<User> optionalUser = userService.getAuthenticatedUser();

		// then
		assertFalse(optionalUser.isPresent());
	}

	@Test
	@DisplayName("getUserById 는 아이디로 User 를 조회한다.")
	void getUserById_Success() {
		// given
		// when
		User result = userService.getUserById(1L);

		// then
		assertNotNull(result);
		assertEquals(user.getId(), result.getId());
		assertEquals(user.getName(), result.getName());
		assertEquals(user.getEmail(), result.getEmail());
	}
}
package com.example.servertech.domain.notification.application;

import com.example.servertech.auth.application.AuthService;
import com.example.servertech.auth.jwt.JwtProperties;
import com.example.servertech.auth.jwt.JwtProvider;
import com.example.servertech.common.event.domain.EventType;
import com.example.servertech.domain.notification.entity.Notification;
import com.example.servertech.domain.notification.exception.NoSuchNotificationException;
import com.example.servertech.domain.notification.exception.ReceiverNotMatchException;
import com.example.servertech.domain.notification.presentation.request.NotificationRequest;
import com.example.servertech.domain.notification.presentation.response.NotificationDetailResponse;
import com.example.servertech.domain.notification.presentation.response.NotificationListResponse;
import com.example.servertech.domain.notification.repository.NotificationRepository;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import com.example.servertech.domain.user.repository.UserRepository;
import com.example.servertech.mock.repository.FakeNotificationRepository;
import com.example.servertech.mock.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.servertech.common.event.domain.EventType.COMMENT_LIKE;
import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationServiceTest {
	private NotificationService notificationService;
	private User user;
	private User notReceiver;
	private final String CONTENT = "테스트 내용";

	@BeforeEach
	void init() {
		NotificationRepository notificationRepository = new FakeNotificationRepository();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserRepository userRepository = new FakeUserRepository();
		JwtProperties jwtProperties = new JwtProperties("testIssuer", "testSecretKey");
		JwtProvider jwtProvider = new JwtProvider(jwtProperties);
		AuthService authService = new AuthService(jwtProvider);

		UserService userService = new UserService(authService, userRepository, encoder);
		notificationService = new NotificationService(userService, notificationRepository);

		user = userRepository.save(
			User.builder()
				.id(1L)
				.role(NORMAL)
				.build()
		);

		notReceiver = userRepository.save(
			User.builder()
				.id(2L)
				.role(NORMAL)
				.build()
		);

		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);

		notificationRepository.save(
			Notification.create(COMMENT_LIKE.getDescription(), CONTENT, EventType.COMMENT_LIKE, user)
		);
	}

	@Test
	@DisplayName("save 는 Notification 객체를 저장한다.")
	void save_Success() {
		// given
		NotificationRequest request = NotificationRequest.builder()
			.type(COMMENT_LIKE)
			.content(CONTENT)
			.receiverId(1L)
			.build();

		// when
		Notification save = notificationService.save(request);

		// then
		assertNotNull(save);
		assertEquals(COMMENT_LIKE, save.getType());
		assertEquals(CONTENT, save.getContent());
		assertEquals(user, save.getReceiver());
		assertEquals(COMMENT_LIKE.getDescription(), save.getTitle());
		assertEquals(2L, save.getId());
	}

	@Test
	@DisplayName("findByLoginUser 는 현재 로그인한 유저의 알림을 모두 조회한다.")
	void findByLoginUser_Success() {
		// given
		// when
		NotificationListResponse response = notificationService.findByLoginUser();

		// then
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(1L, response.notificationResponseList().get(0).id());
		assertEquals(COMMENT_LIKE.getDescription(), response.notificationResponseList().get(0).title());
	}

	@Test
	@DisplayName("findByLoginUser 는 로그인 허지 않은 유저의 경우 빈 리스트를 반환한다.")
	void findByLoginUser_EmptyResponse() {
		// given
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);

		// when
		NotificationListResponse response = notificationService.findByLoginUser();

		// then
		assertNotNull(response);
		assertEquals(0, response.size());
		assertTrue(response.notificationResponseList().isEmpty());
	}

	@Test
	@DisplayName("findById 는 해당 아이디로 알림을 조회한다.")
	void findById_Success() {
		// when
		NotificationDetailResponse response = notificationService.findById(1L);

		// then
		assertNotNull(response);
		assertEquals(COMMENT_LIKE.getDescription(), response.title());
		assertEquals(CONTENT, response.content());
		assertEquals(COMMENT_LIKE, response.type());
	}

	@Test
	@DisplayName("findById 는 존재하지 않는 아이디로 조회하면 NoSuchNotificationException 을 반환한다.")
	void findById_throw_NoSuchNotificationException() {
		// when
		// then
		assertThatThrownBy(() -> notificationService.findById(2L))
			.isInstanceOf(NoSuchNotificationException.class);
	}

	@Test
	@DisplayName("findById 는 다른 유저의 알림을 조회하면 ReceiverNotMatchException 을 반환한다.")
	void findById_throw_ReceiverNotMatchException() {
		// given
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(notReceiver, notReceiver.getPassword(), notReceiver.getAuthorities())
		);

		// when
		// then
		assertThatThrownBy(() -> notificationService.findById(1L))
			.isInstanceOf(ReceiverNotMatchException.class);
	}
}
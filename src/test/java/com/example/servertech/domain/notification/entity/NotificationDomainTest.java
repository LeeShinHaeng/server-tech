package com.example.servertech.domain.notification.entity;

import com.example.servertech.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static com.example.servertech.domain.notification.entity.NotificationType.COMMENT_LIKE;
import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.junit.jupiter.api.Assertions.*;

class NotificationDomainTest {
	private Notification notification;

	private User user;
	private final String TITLE = "테스트 제목";
	private final String CONTENT = "테스트 내용";

	@BeforeEach
	void init() {
		String USER_NAME = "이신행";
		String EMAIL = "user@example.com";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String RAW_PASSWORD = "password";
		String ENCODED_PASSWORD = encoder.encode(RAW_PASSWORD);
		user = User.builder()
			.id(1L)
			.name(USER_NAME)
			.email(EMAIL)
			.password(ENCODED_PASSWORD)
			.role(NORMAL)
			.build();

		notification = Notification.builder()
			.id(1L)
			.title(TITLE)
			.content(CONTENT)
			.type(COMMENT_LIKE)
			.receiver(user)
			.isRead(false)
			.createTime(LocalDateTime.now())
			.build();
	}

	@Test
	void create() {
		// when
		Notification created = Notification.create(TITLE, CONTENT, COMMENT_LIKE, user);

		// then
		assertNotNull(created);
		assertEquals(TITLE, created.getTitle());
		assertEquals(CONTENT, created.getContent());
		assertEquals(COMMENT_LIKE, created.getType());
		assertEquals(user, created.getReceiver());
	}

	@Test
	void read() {
		// when
		assertEquals(false, notification.getIsRead());
		notification.read();

		// then
		assertEquals(true, notification.getIsRead());
	}
}
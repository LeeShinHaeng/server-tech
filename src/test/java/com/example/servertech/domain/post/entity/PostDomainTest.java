package com.example.servertech.domain.post.entity;

import com.example.servertech.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostDomainTest {
	private Post post;
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

		post = Post.builder()
			.id(1L)
			.writer(user)
			.title(TITLE)
			.content(CONTENT)
			.build();
	}

	@Test
	@DisplayName("create 는 Post 객체를 생성한다.")
	void create_Success() {
		// when
		Post created = Post.create(user, TITLE, CONTENT);

		//then
		assertNotNull(created);
		assertEquals(user, created.getWriter());
		assertEquals(TITLE, created.getTitle());
		assertEquals(CONTENT, created.getContent());
	}

	@Test
	@DisplayName("updateTitle 는 Post 의 제목을 변경한다.")
	void updateTitle_Success() {
		// given
		String newTitle = "new title";

		// when
		post.updateTitle(newTitle);

		//then
		assertNotEquals(TITLE, post.getTitle());
		assertEquals(newTitle, post.getTitle());
	}

	@Test
	@DisplayName("updateContent 는 Post 의 내용을 변경한다.")
	void updateContent_Success() {
		// given
		String newContent = "new content";

		// when
		post.updateContent(newContent);

		//then
		assertNotEquals(CONTENT, post.getContent());
		assertEquals(newContent, post.getContent());
	}
}
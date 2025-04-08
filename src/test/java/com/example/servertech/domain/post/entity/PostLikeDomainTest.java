package com.example.servertech.domain.post.entity;

import com.example.servertech.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostLikeDomainTest {
	private Post post;
	private User user;

	@BeforeEach
	void init() {
		String USER_NAME = "이신행";
		String EMAIL = "user@example.com";
		String RAW_PASSWORD = "password";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String ENCODED_PASSWORD = encoder.encode(RAW_PASSWORD);
		user = User.builder()
			.id(1L)
			.name(USER_NAME)
			.email(EMAIL)
			.password(ENCODED_PASSWORD)
			.role(NORMAL)
			.build();

		String TITLE = "테스트 제목";
		String CONTENT = "테스트 내용";
		post = Post.builder()
			.id(1L)
			.writer(user)
			.title(TITLE)
			.content(CONTENT)
			.build();
	}

	@Test
	@DisplayName("create 는 PostLike 객체를 생성한다.")
	void create_Success() {
		// when
		PostLike postLike = PostLike.create(post, user);

		// then
		assertEquals(user, postLike.getLiker());
		assertEquals(post, postLike.getPost());
	}
}
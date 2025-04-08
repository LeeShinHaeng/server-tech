package com.example.servertech.domain.comment.entity;

import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommentDomainTest {
	private Comment comment;
	private final String CONTENT = "content";
	private User user;
	private Post post;

	@BeforeEach
	void init() {
		user = User.builder()
			.id(1L)
			.role(NORMAL)
			.build();

		post = Post.builder()
			.id(1L)
			.writer(user)
			.build();

		comment = Comment.builder()
			.id(1L)
			.writer(user)
			.post(post)
			.content(CONTENT)
			.build();
	}

	@Test
	@DisplayName("create 는 Comment 객체를 생성한다.")
	void create_Success() {
		// when
		Comment created = Comment.create(post, user, CONTENT);

		// then
		assertNotNull(created);
		assertEquals(created.getContent(), CONTENT);
		assertEquals(created.getPost(), post);
		assertEquals(created.getWriter(), user);
	}

	@Test
	@DisplayName("updateComment 는 Comment 객체의 content를 수정한다.")
	void updateContent_Success() {
		// given
		String newContent = "new content";

		// when
		comment.updateContent(newContent);

		// then
		assertNotEquals(CONTENT, comment.getContent());
		assertEquals(newContent, comment.getContent());
	}
}
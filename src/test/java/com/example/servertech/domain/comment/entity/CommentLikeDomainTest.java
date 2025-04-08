package com.example.servertech.domain.comment.entity;

import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommentLikeDomainTest {
	private Comment comment;
	private User user;

	@BeforeEach
	void init() {
		user = User.builder()
			.id(1L)
			.role(NORMAL)
			.build();

		Post post = Post.builder()
			.id(1L)
			.writer(user)
			.build();

		String CONTENT = "content";
		comment = Comment.builder()
			.id(1L)
			.writer(user)
			.post(post)
			.content(CONTENT)
			.build();
	}

	@Test
	@DisplayName("create 는 CommentLike 객체를 생성한다.")
	void create_Success() {
		// when
		CommentLike commentLike = CommentLike.create(comment, user);

		// then
		assertNotNull(commentLike);
		assertEquals(comment, commentLike.getComment());
		assertEquals(user, commentLike.getLiker());
	}
}
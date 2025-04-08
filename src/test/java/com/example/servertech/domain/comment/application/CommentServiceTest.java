package com.example.servertech.domain.comment.application;

import com.example.servertech.auth.application.AuthService;
import com.example.servertech.auth.jwt.JwtProperties;
import com.example.servertech.auth.jwt.JwtProvider;
import com.example.servertech.domain.comment.entity.Comment;
import com.example.servertech.domain.comment.entity.CommentLike;
import com.example.servertech.domain.comment.exception.WriterNotMatchException;
import com.example.servertech.domain.comment.presentation.request.CommentCreateRequest;
import com.example.servertech.domain.comment.presentation.response.CommentListResponse;
import com.example.servertech.domain.comment.presentation.response.CommentPersistResponse;
import com.example.servertech.domain.comment.repository.CommentLikeRepository;
import com.example.servertech.domain.comment.repository.CommentRepository;
import com.example.servertech.domain.post.application.PostService;
import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.post.repository.PostLikeRepository;
import com.example.servertech.domain.post.repository.PostRepository;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import com.example.servertech.domain.user.repository.UserRepository;
import com.example.servertech.mock.repository.FakeCommentLikeRepository;
import com.example.servertech.mock.repository.FakeCommentRepository;
import com.example.servertech.mock.repository.FakePostLikeRepository;
import com.example.servertech.mock.repository.FakePostRepository;
import com.example.servertech.mock.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommentServiceTest {
	private CommentService commentService;

	private final String CONTENT = "테스트 내용";
	private User user;
	private Post post;
	private Comment comment;
	private CommentRepository commentRepository;
	private CommentLikeRepository commentLikeRepository;
	private UserRepository userRepository;

	@BeforeEach
	void init() {
		commentRepository = new FakeCommentRepository();
		commentLikeRepository = new FakeCommentLikeRepository();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		PostRepository postRepository = new FakePostRepository();
		PostLikeRepository postLikeRepository = new FakePostLikeRepository();

		userRepository = new FakeUserRepository();
		JwtProperties jwtProperties = new JwtProperties("testIssuer", "testSecretKey");
		JwtProvider jwtProvider = new JwtProvider(jwtProperties);
		AuthService authService = new AuthService(jwtProvider);
		UserService userService = new UserService(authService, userRepository, encoder);
		PostService postService = new PostService(userService, postRepository, postLikeRepository);

		commentService = new CommentService(userService, postService, commentRepository, commentLikeRepository);

		user = userRepository.save(
			User.builder()
				.id(1L)
				.role(NORMAL)
				.build()
		);

		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);

		post = postRepository.save(
			Post.builder()
				.id(1L)
				.writer(user)
				.build()
		);

		comment = commentRepository.save(
			Comment.create(post, user, CONTENT)
		);
	}

	@Test
	@DisplayName("create 는 Comment 객체를 저장한다.")
	void create_Success() {
		// given
		CommentCreateRequest request = CommentCreateRequest.builder()
			.content(CONTENT)
			.build();

		// when
		CommentPersistResponse response = commentService.create(1L, request);

		// then
		assertNotNull(response);
		assertEquals(2L, response.id());
	}

	@Test
	@DisplayName("findAllByPostId 는 post 에 달린 모든 댓글을 조회한다.")
	void findAllByPostId_Success() {
		// when
		CommentListResponse response = commentService.findAllByPostId(post.getId());

		// then
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(1L, response.commentResponseList().get(0).id());
		assertEquals(CONTENT, response.commentResponseList().get(0).content());
		assertEquals(user.getName(), response.commentResponseList().get(0).writerName());
		assertEquals(false, response.commentResponseList().get(0).like());
	}

	@Test
	@DisplayName("findAllByPostId 는 like 한 댓글의 like 는 true를 반환한다.")
	void findAllByPostId_Like() {
		// when
		commentService.like(1L);
		CommentListResponse response = commentService.findAllByPostId(post.getId());

		// then
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(1L, response.commentResponseList().get(0).id());
		assertEquals(CONTENT, response.commentResponseList().get(0).content());
		assertEquals(user.getName(), response.commentResponseList().get(0).writerName());
		assertEquals(true, response.commentResponseList().get(0).like());
	}

	@Test
	@DisplayName("update 는 Comment 객체를 수정한다.")
	void update_Success() {
		// given
		String newContent = "new content";
		CommentCreateRequest request = CommentCreateRequest.builder()
			.content(newContent)
			.build();

		// when
		commentService.update(1L, request);
		String content = commentRepository.findById(1L).get().getContent();

		// then
		assertNotEquals(CONTENT, content);
		assertEquals(newContent, content);
	}

	@Test
	@DisplayName("update 는 다른 유저의 Comment 객체를 수정하면 WriterNotMatchException 를 반환한다.")
	void update_throw_WriterNotMatchException() {
		// given
		User writer = userRepository.save(User.builder().id(2L).build());
		commentRepository.save(Comment.create(post, writer, CONTENT));
		CommentCreateRequest request = CommentCreateRequest.builder().build();

		// when
		// then
		assertThatThrownBy(() -> commentService.update(2L, request))
			.isInstanceOf(WriterNotMatchException.class);
	}

	@Test
	@DisplayName("delete 는 Comment 객체의 deleteAt 을 현재 시간으로 설정한다.")
	void delete() {
		// when
		commentService.delete(1L);
		Optional<Comment> deleted = commentRepository.findById(1L);

		// then
		assertNotNull(deleted.get().getDeletedAt());
	}

	@Test
	@DisplayName("delete 는 다른 유저의 Comment 객체를 삭제하면 WriterNotMatchException 를 반환한다.")
	void delete_throw_WriterNotMatchException() {
		// given
		User writer = userRepository.save(User.builder().id(2L).build());
		commentRepository.save(Comment.create(post, writer, CONTENT));

		// when
		// then
		assertThatThrownBy(() -> commentService.delete(2L))
			.isInstanceOf(WriterNotMatchException.class);
	}

	@Test
	@DisplayName("like 는 CommentLike 객체를 생성합니다.")
	void like() {
		// given
		List<Long> commentIdList = new ArrayList<>();
		commentIdList.add(1L);

		// when
		commentService.like(1L);
		List<CommentLike> response = commentLikeRepository.findAllByCommentIdInAndLikerId(commentIdList, 1L);

		// then
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(1, response.get(0).getId());
		assertEquals(comment, response.get(0).getComment());
		assertEquals(user, response.get(0).getLiker());
	}

	@Test
	@DisplayName("unlike 는 CommentLike 객체를 삭제합니다.")
	void unlike() {
		// given
		List<Long> commentIdList = new ArrayList<>();
		commentIdList.add(1L);

		// when
		commentService.like(1L);
		commentService.unlike(1L);
		List<CommentLike> response = commentLikeRepository.findAllByCommentIdInAndLikerId(commentIdList, 1L);

		// then
		assertNotNull(response);
		assertEquals(0, response.size());
	}
}
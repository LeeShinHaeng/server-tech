package com.example.servertech.domain.post.application;

import com.example.servertech.auth.application.AuthService;
import com.example.servertech.auth.jwt.JwtProperties;
import com.example.servertech.auth.jwt.JwtProvider;
import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.post.entity.PostLike;
import com.example.servertech.domain.post.exception.NoSuchPostException;
import com.example.servertech.domain.post.exception.WriterNotMatchException;
import com.example.servertech.domain.post.presentation.request.PostCreateRequest;
import com.example.servertech.domain.post.presentation.response.PostDetailResponse;
import com.example.servertech.domain.post.presentation.response.PostListResponse;
import com.example.servertech.domain.post.presentation.response.PostPersistResponse;
import com.example.servertech.domain.post.repository.PostLikeRepository;
import com.example.servertech.domain.post.repository.PostRepository;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import com.example.servertech.domain.user.repository.UserRepository;
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

import java.util.Optional;

import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostServiceTest {
	private PostService postService;
	private UserRepository userRepository;
	private PostRepository postRepository;
	private PostLikeRepository postLikeRepository;

	private final String TITLE = "테스트 제목";
	private final String CONTENT = "테스트 내용";
	private Post post;

	@BeforeEach
	void init() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String USER_NAME = "이신행";
		String EMAIL = "user@example.com";
		String RAW_PASSWORD = "password";

		postRepository = new FakePostRepository();
		postLikeRepository = new FakePostLikeRepository();

		userRepository = new FakeUserRepository();
		JwtProperties jwtProperties = new JwtProperties("testIssuer", "testSecretKey");
		JwtProvider jwtProvider = new JwtProvider(jwtProperties);
		AuthService authService = new AuthService(jwtProvider);

		UserService userService = new UserService(authService, userRepository, encoder);

		postService = new PostService(userService, postRepository, postLikeRepository);

		User user = userRepository.save(
			User.builder()
				.id(1L)
				.name(USER_NAME)
				.email(EMAIL)
				.password(encoder.encode(RAW_PASSWORD))
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
				.title(TITLE)
				.content(CONTENT)
				.build()
		);
	}

	@Test
	@DisplayName("create 는 Post 객체를 저장한다.")
	void create_Success() {
		// given
		PostCreateRequest build = PostCreateRequest.builder()
			.title(TITLE)
			.content(CONTENT)
			.build();

		// when
		PostPersistResponse response = postService.create(build);

		// then
		assertNotNull(response);
		assertEquals(2L, response.id());
	}

	@Test
	@DisplayName("findAll 은 Post 객체를 모두 조회한다.")
	void findAll_Success() {
		// when
		PostListResponse all = postService.findAll();

		// then
		assertNotNull(all);
		assertEquals(1, all.size());
		assertEquals(1L, all.postResponseList().get(0).id());
		assertEquals(TITLE, all.postResponseList().get(0).title());
	}

	@Test
	@DisplayName("findById 은 해당 아이디의 Post 객체를 조회해 Response 객체로 반환한다.")
	void findById_Success() {
		// when
		PostDetailResponse response = postService.findById(1L);

		// then
		assertNotNull(response);
		assertEquals(TITLE, response.title());
		assertEquals(CONTENT, response.contents());
		assertEquals(false, response.like());
	}

	@Test
	@DisplayName("findById 은 좋아요를 누른 Post Response 의 like 는 true 로 반환한다.")
	void findById_Like() {
		// when
		postService.like(1L);
		PostDetailResponse response = postService.findById(1L);

		// then
		assertNotNull(response);
		assertEquals(true, response.like());
	}

	@Test
	@DisplayName("update 은 Post 객체를 수정한다.")
	void update_Success() {
		// given
		String newTitle = "new title";
		String newContent = "new content";
		PostCreateRequest request = PostCreateRequest.builder()
			.title(newTitle)
			.content(newContent)
			.build();

		// when
		postService.update(1L, request);

		// then
		assertNotEquals(TITLE, post.getTitle());
		assertNotEquals(CONTENT, post.getContent());
		assertEquals(newTitle, post.getTitle());
		assertEquals(newContent, post.getContent());
	}

	@Test
	@DisplayName("update 은 다른 유저의 Post 객체를 수정하면 WriterNotMatchException 를 반환한다.")
	void update_throw_WriterNotMatchException() {
		// given
		User writer = userRepository.save(User.builder().id(2L).build());
		postRepository.save(Post.create(writer, TITLE, CONTENT));
		PostCreateRequest request = PostCreateRequest.builder().build();

		// when
		// then
		assertThatThrownBy(() -> postService.update(2L, request))
			.isInstanceOf(WriterNotMatchException.class);
	}

	@Test
	@DisplayName("delete 은 Post 객체의 DeletedAt 을 현재로 설정한다.")
	void delete_Success() {
		// when
		postService.delete(1L);
		Post deleted = postService.findPostById(1L);

		// then
		assertNotNull(deleted.getDeletedAt());
	}

	@Test
	@DisplayName("delete 은 다른 유저의 Post 객체를 삭제하면 WriterNotMatchException 를 반환한다.")
	void delete_throw_WriterNotMatchException() {
		// when
		User writer = userRepository.save(User.builder().id(2L).build());
		postRepository.save(Post.create(writer, TITLE, CONTENT));

		// then
		assertThatThrownBy(() -> postService.delete(2L))
			.isInstanceOf(WriterNotMatchException.class);
	}

	@Test
	@DisplayName("findPostById 은 해당 아이디의 Post 객체를 조회한다.")
	void findPostById_Success() {
		// when
		Post found = postService.findPostById(1L);

		// then
		assertNotNull(found);
		assertEquals(post, found);
	}

	@Test
	@DisplayName("findPostById 은 존재하지 않는 아이디의 Post 객체를 조회하면 NoSuchPostException 을 반환한다.")
	void findPostById_throw_NoSuchPostException() {
		// when
		// then
		assertThatThrownBy(() -> postService.findPostById(2L))
			.isInstanceOf(NoSuchPostException.class);
	}

	@Test
	@DisplayName("like 은 PostLike 객체를 생성한다.")
	void like() {
		// given
		// when
		postService.like(1L);
		Optional<PostLike> postLike = postLikeRepository.findByPostAndUser(1L, 1L);

		// then
		assertNotNull(postLike);
		assertEquals(1L, postLike.get().getId());
	}

	@Test
	@DisplayName("unlike 은 PostLike 객체를 삭제한다.")
	void unlike() {
		// given
		// when
		postService.like(1L);
		postService.unlike(1L);
		Optional<PostLike> postLike = postLikeRepository.findByPostAndUser(1L, 1L);

		// then
		assertEquals(Optional.empty(), postLike);
	}
}
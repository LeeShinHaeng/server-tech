package com.example.servertech.domain.post.application;

import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.post.entity.PostLike;
import com.example.servertech.domain.post.presentation.request.PostCreateRequest;
import com.example.servertech.domain.post.presentation.response.PostDetailResponse;
import com.example.servertech.domain.post.presentation.response.PostListResponse;
import com.example.servertech.domain.post.presentation.response.PostPersistResponse;
import com.example.servertech.domain.post.repository.PostLikeRepository;
import com.example.servertech.domain.post.repository.PostRepository;
import com.example.servertech.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
	private final UserService userService;
	private final PostRepository postRepository;
	private final PostLikeRepository postLikeRepository;

	@Transactional
	public PostPersistResponse create(PostCreateRequest request) {
		Post save = postRepository.save(
			Post.create(
				userService.me(),
				request.title(),
				request.content()
			)
		);
		return PostPersistResponse.create(save.getId());
	}

	@Transactional(readOnly = true)
	public PostListResponse findAll() {
		List<Post> posts = postRepository.findAll();
		return PostListResponse.create(posts);
	}

	@Transactional(readOnly = true)
	public PostDetailResponse findById(Long id) {
		Post post = findPostById(id);
		return PostDetailResponse.create(post);
	}

	@Transactional
	public void update(Long id, PostCreateRequest request) {
		Post post = findPostById(id);

		if (!post.getWriter().getId().equals(userService.me().getId())) {
			throw new RuntimeException("작성자와 로그인한 유저가 일치하지 않습니다");
		}

		post.updateTitle(request.title());
		post.updateContent(request.content());
	}

	@Transactional
	public void delete(Long id) {
		Post post = findPostById(id);

		if (!post.getWriter().getId().equals(userService.me().getId())) {
			throw new RuntimeException("작성자와 로그인한 유저가 일치하지 않습니다");
		}

		post.delete();
	}

	public Post findPostById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 아이디의 게시글이 존재하지 않습니다"));
	}

	@Transactional
	public void like(Long id) {
		postLikeRepository.save(
			PostLike.create(
				findPostById(id),
				userService.me()
			)
		);
	}

	@Transactional
	public void unlike(Long id) {
		postLikeRepository.deleteById(id);
	}
}

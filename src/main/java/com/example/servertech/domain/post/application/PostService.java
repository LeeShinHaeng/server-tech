package com.example.servertech.domain.post.application;

import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.post.presentation.request.PostCreateRequest;
import com.example.servertech.domain.post.presentation.response.PostDetailResponse;
import com.example.servertech.domain.post.presentation.response.PostListResponse;
import com.example.servertech.domain.post.presentation.response.PostPersistResponse;
import com.example.servertech.domain.post.repository.PostRepository;
import com.example.servertech.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
	private final UserService userService;
	private final PostRepository postRepository;

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

	public PostListResponse findAll() {
		return null;
	}

	public PostDetailResponse findById(Long id) {
		return null;
	}

	public void update(Long id, PostCreateRequest request) {

	}

	public void delete(Long id) {

	}
}

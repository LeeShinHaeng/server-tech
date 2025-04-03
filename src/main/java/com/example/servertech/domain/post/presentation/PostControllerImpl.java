package com.example.servertech.domain.post.presentation;

import com.example.servertech.domain.post.application.PostService;
import com.example.servertech.domain.post.presentation.request.PostCreateRequest;
import com.example.servertech.domain.post.presentation.response.PostDetailResponse;
import com.example.servertech.domain.post.presentation.response.PostListResponse;
import com.example.servertech.domain.post.presentation.response.PostPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostControllerImpl implements PostController {
	private final PostService postService;

	@Override
	@PostMapping
	public ResponseEntity<PostPersistResponse> write(PostCreateRequest request) {
		return null;
	}

	@Override
	@GetMapping
	public ResponseEntity<PostListResponse> getAll() {
		return null;
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<PostDetailResponse> get(@PathVariable Long id) {
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id) {
		return null;
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		return null;
	}
}

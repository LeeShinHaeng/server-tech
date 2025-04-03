package com.example.servertech.domain.comment.presentation;

import com.example.servertech.domain.comment.application.CommentService;
import com.example.servertech.domain.comment.presentation.request.CommentCreateRequest;
import com.example.servertech.domain.comment.presentation.response.CommentDetailResponse;
import com.example.servertech.domain.comment.presentation.response.CommentPersistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentControllerImpl implements CommentController {
	private final CommentService commentService;

	@Override
	@PostMapping("/{postId}")
	public ResponseEntity<CommentPersistResponse> write(
		@PathVariable Long postId,
		@RequestBody CommentCreateRequest request) {
		return null;
	}

	@Override
	@GetMapping("/{postId}")
	public ResponseEntity<CommentDetailResponse> getByPostId(@PathVariable Long postId) {
		return null;
	}

	@Override
	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(
		@PathVariable Long id,
		@RequestBody CommentCreateRequest request) {
		return null;
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		return null;
	}
}

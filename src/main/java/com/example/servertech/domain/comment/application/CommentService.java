package com.example.servertech.domain.comment.application;

import com.example.servertech.domain.comment.entity.Comment;
import com.example.servertech.domain.comment.presentation.request.CommentCreateRequest;
import com.example.servertech.domain.comment.presentation.response.CommentListResponse;
import com.example.servertech.domain.comment.presentation.response.CommentPersistResponse;
import com.example.servertech.domain.comment.repository.CommentRepository;
import com.example.servertech.domain.post.application.PostService;
import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final UserService userService;
	private final PostService postService;
	private final CommentRepository commentRepository;

	@Transactional
	public CommentPersistResponse create(Long postId, CommentCreateRequest request) {
		User me = userService.me();
		Post post = postService.findPostById(postId);
		Comment save = commentRepository.save(
			Comment.create(post, me, request.content())
		);
		return new CommentPersistResponse(save.getId());
	}

	@Transactional(readOnly = true)
	public CommentListResponse findAllByPostId(Long postId) {
		List<Comment> commentList = commentRepository.findByPostId(postId);
		return CommentListResponse.create(commentList);
	}

	@Transactional
	public void update(Long id, CommentCreateRequest request) {
		User me = userService.me();
		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 아이디의 댓글이 존재하지 않습니다"));

		if (!comment.getWriter().getId().equals(me.getId())) {
			throw new RuntimeException("작성자와 로그인한 유저가 일치하지 않습니다");
		}

		comment.updateContent(request.content());
	}

	@Transactional
	public void delete(Long id) {
		User me = userService.me();
		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 아이디의 댓글이 존재하지 않습니다"));

		if (!comment.getWriter().getId().equals(me.getId())) {
			throw new RuntimeException("작성자와 로그인한 유저가 일치하지 않습니다");
		}

		comment.delete();
	}
}

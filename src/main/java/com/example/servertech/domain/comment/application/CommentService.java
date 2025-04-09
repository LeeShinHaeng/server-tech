package com.example.servertech.domain.comment.application;

import com.example.servertech.domain.comment.entity.Comment;
import com.example.servertech.domain.comment.entity.CommentLike;
import com.example.servertech.domain.comment.exception.NoSuchCommentException;
import com.example.servertech.domain.comment.exception.WriterNotMatchException;
import com.example.servertech.domain.comment.presentation.request.CommentCreateRequest;
import com.example.servertech.domain.comment.presentation.response.CommentListResponse;
import com.example.servertech.domain.comment.presentation.response.CommentPersistResponse;
import com.example.servertech.domain.comment.repository.CommentLikeRepository;
import com.example.servertech.domain.comment.repository.CommentRepository;
import com.example.servertech.domain.post.application.PostService;
import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.servertech.domain.user.entity.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final UserService userService;
	private final PostService postService;
	private final CommentRepository commentRepository;
	private final CommentLikeRepository commentLikeRepository;

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

		User me = userService.getAuthenticatedUser().orElse(null);
		Set<Long> likedCommentIds;
		if (me != null) {
			likedCommentIds = commentLikeRepository
				.findAllByCommentIdInAndLikerId(
					commentList.stream().map(Comment::getId).toList(),
					me.getId()
				)
				.stream()
				.map(cl -> cl.getComment().getId())
				.collect(Collectors.toSet());
		} else {
			likedCommentIds = Collections.emptySet();
		}

		Map<Comment, Boolean> commentMap = commentList
			.stream()
			.collect(
				Collectors.toMap(
					comment -> comment,
					comment -> likedCommentIds.contains(comment.getId())
				)
			);

		return CommentListResponse.create(commentMap);
	}

	@Transactional
	public void update(Long id, CommentCreateRequest request) {
		Comment comment = getComment(id);
		checkAuth(comment);
		comment.updateContent(request.content());
	}

	@Transactional
	public void delete(Long id) {
		Comment comment = getComment(id);
		checkAuth(comment);
		comment.delete();
	}

	public void checkAuth(Comment comment) {
		User me = userService.me();
		if (!comment.getWriter().getId().equals(me.getId()) && me.getRole() != ADMIN)
			throw new WriterNotMatchException();
	}

	@Transactional(readOnly = true)
	public Comment getComment(Long id) {
		return commentRepository.findById(id)
			.orElseThrow(NoSuchCommentException::new);
	}

	@Transactional
	public void like(Long commentId) {
		commentLikeRepository.save(
			CommentLike.create(
				getComment(commentId),
				userService.me()
			)
		);
	}

	@Transactional
	public void unlike(Long commentId) {
		commentLikeRepository.deleteByCommentAndUser(commentId, userService.me().getId());
	}
}

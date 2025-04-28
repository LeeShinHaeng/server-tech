package com.example.servertech.domain.comment.application;

import com.example.servertech.common.event.domain.CommonEvent;
import com.example.servertech.common.event.producer.EventProducer;
import com.example.servertech.domain.comment.entity.Comment;
import com.example.servertech.domain.comment.entity.CommentLike;
import com.example.servertech.domain.comment.exception.NoSuchCommentException;
import com.example.servertech.domain.comment.exception.WriterNotMatchException;
import com.example.servertech.domain.comment.presentation.request.CommentCreateRequest;
import com.example.servertech.domain.comment.presentation.response.CommentListResponse;
import com.example.servertech.domain.comment.presentation.response.CommentPersistResponse;
import com.example.servertech.domain.comment.repository.CommentLikeRepository;
import com.example.servertech.domain.comment.repository.CommentRepository;
import com.example.servertech.domain.common.entity.exception.LockInterruptedException;
import com.example.servertech.domain.common.entity.exception.TryLockFailureException;
import com.example.servertech.domain.post.application.PostService;
import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.servertech.common.event.domain.EventType.COMMENT_LIKE;
import static com.example.servertech.common.event.domain.EventType.POST_COMMENTED;
import static com.example.servertech.domain.user.entity.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final UserService userService;
	private final PostService postService;
	private final CommentRepository commentRepository;
	private final CommentLikeRepository commentLikeRepository;
	private final EventProducer eventProducer;
	private final RedissonClient redissonClient;

	private static final String LOCK_KEY_PREFIX = "lock:commentlike:";

	@Transactional
	public CommentPersistResponse create(Long postId, CommentCreateRequest request) {
		User me = userService.me();
		Post post = postService.findPostById(postId);
		Comment save = commentRepository.save(
			Comment.create(post, me, request.content())
		);
		eventProducer.publish(
			CommonEvent.create(post.getWriter().getId(), POST_COMMENTED)
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
		String lockKey = LOCK_KEY_PREFIX + commentId;
		RLock lock = redissonClient.getLock(lockKey);

		try {
			if (!lock.tryLock(1, 2, TimeUnit.SECONDS))
				throw new TryLockFailureException();

			Comment comment = getComment(commentId);
			commentLikeRepository.save(
				CommentLike.create(comment, userService.me())
			);
			eventProducer.publish(
				CommonEvent.create(comment.getWriter().getId(), COMMENT_LIKE)
			);
		} catch (InterruptedException e) {
			throw new LockInterruptedException();
		} finally {
			if (lock != null && lock.isLocked()) lock.unlock();
		}
	}

	@Transactional
	public void unlike(Long commentId) {
		String lockKey = LOCK_KEY_PREFIX + commentId;
		RLock lock = redissonClient.getLock(lockKey);

		try {
			if (!lock.tryLock(1, 2, TimeUnit.SECONDS))
				throw new TryLockFailureException();

			commentLikeRepository.deleteByCommentAndUser(commentId, userService.me().getId());
		} catch (InterruptedException e) {
			throw new LockInterruptedException();
		} finally {
			if (lock != null && lock.isLocked()) lock.unlock();
		}
	}
}

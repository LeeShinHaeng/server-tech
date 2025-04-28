package com.example.servertech.domain.post.application;

import com.example.servertech.common.event.domain.CommonEvent;
import com.example.servertech.common.event.producer.EventProducer;
import com.example.servertech.domain.common.entity.exception.LockInterruptedException;
import com.example.servertech.domain.common.entity.exception.TryLockFailureException;
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
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.servertech.common.event.domain.EventType.POST_LIKE;
import static com.example.servertech.domain.user.entity.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
public class PostService {
	private final UserService userService;
	private final PostRepository postRepository;
	private final PostLikeRepository postLikeRepository;
	private final EventProducer eventProducer;
	private final RedissonClient redissonClient;

	private static final String LOCK_KEY_PREFIX = "lock:postlike:";

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

		boolean liked = false;
		User user = userService.getAuthenticatedUser().orElse(null);
		if (user != null) {
			liked = postLikeRepository.findByPostAndUser(post.getId(), user.getId())
				.isPresent();
		}

		return PostDetailResponse.create(post, liked);
	}

	@Transactional
	public void update(Long id, PostCreateRequest request) {
		Post post = findPostById(id);
		checkAuth(post);
		post.updateTitle(request.title());
		post.updateContent(request.content());
	}

	@Transactional
	public void delete(Long id) {
		Post post = findPostById(id);
		checkAuth(post);
		post.delete();
	}

	public void checkAuth(Post post) {
		User me = userService.me();
		if (!post.getWriter().getId().equals(me.getId()) && me.getRole() != ADMIN)
			throw new WriterNotMatchException();
	}

	@Transactional(readOnly = true)
	public Post findPostById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(NoSuchPostException::new);
	}

	@Transactional
	public void like(Long id) {
		String lockKey = LOCK_KEY_PREFIX + id;
		RLock lock = redissonClient.getLock(lockKey);
		try {
			if (!lock.tryLock(1, 2, TimeUnit.SECONDS))
				throw new TryLockFailureException();

			Post post = findPostById(id);
			postLikeRepository.save(
				PostLike.create(post, userService.me())
			);
			eventProducer.publish(
				CommonEvent.create(post.getWriter().getId(), POST_LIKE)
			);
		} catch (InterruptedException e) {
			throw new LockInterruptedException();
		} finally {
			if (lock != null && lock.isLocked()) lock.unlock();
		}
	}

	@Transactional
	public void unlike(Long id) {
		String lockKey = LOCK_KEY_PREFIX + id;
		RLock lock = redissonClient.getLock(lockKey);
		try {
			if (!lock.tryLock(1, 2, TimeUnit.SECONDS))
				throw new TryLockFailureException();
			postLikeRepository.deleteByPostAndUser(id, userService.me().getId());
		} catch (InterruptedException e) {
			throw new LockInterruptedException();
		} finally {
			if (lock != null && lock.isLocked()) lock.unlock();
		}
	}
}

package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepository {
	private final JpaPostLikeRepository jpaRepository;

	@Override
	public PostLike save(PostLike postLike) {
		return jpaRepository.save(postLike);
	}

	@Override
	public void deleteByPostAndUser(Long postId, Long userId) {
		jpaRepository.deleteByPostIdAndLikerId(postId, userId);
	}

	@Override
	public Optional<PostLike> findByPostAndUser(Long postId, Long userId) {
		return jpaRepository.findByPostIdAndLikerId(postId, userId);
	}

	@Override
	public Integer countByPostId(Long postId) {
		return jpaRepository.countByPostId(postId);
	}
}

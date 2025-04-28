package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.PostLike;

import java.util.Optional;

public interface PostLikeRepository {
	PostLike save(PostLike postLike);

	void deleteByPostAndUser(Long postId, Long userId);

	Optional<PostLike> findByPostAndUser(Long postId, Long userId);

	Integer countByPostId(Long postId);
}

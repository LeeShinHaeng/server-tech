package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPostLikeRepository extends JpaRepository<PostLike, Long> {
	Optional<PostLike> findByPostIdAndLikerId(Long postId, Long likerId);

	void deleteByPostIdAndLikerId(Long postId, Long likerId);

	Integer countByPostId(Long postId);
}

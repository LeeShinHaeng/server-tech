package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCommentLikeRepository extends JpaRepository<CommentLike, Long> {
	void deleteByCommentIdAndLikerId(Long commentId, Long likerId);

	Optional<CommentLike> findByCommentIdAndLikerId(Long commentId, Long likerId);
}

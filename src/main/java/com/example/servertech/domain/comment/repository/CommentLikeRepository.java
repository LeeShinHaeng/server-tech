package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;

import java.util.Optional;

public interface CommentLikeRepository {
	CommentLike save(CommentLike commentLike);

	void deleteByCommentAndUser(Long commentId, Long userId);

    Optional<CommentLike> findByCommentIdAndLikerId(Long commentId, Long userId);
}

package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;

import java.util.List;

public interface CommentLikeRepository {
	CommentLike save(CommentLike commentLike);

	void deleteByCommentAndUser(Long commentId, Long userId);

	List<CommentLike> findAllByCommentIdInAndLikerId(List<Long> commentIds, Long likerId);

	Integer countByCommentId(Long commentId);
}

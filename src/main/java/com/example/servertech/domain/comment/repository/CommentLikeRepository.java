package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;

public interface CommentLikeRepository {
	CommentLike save(CommentLike commentLike);

	void deleteById(Long id);
}

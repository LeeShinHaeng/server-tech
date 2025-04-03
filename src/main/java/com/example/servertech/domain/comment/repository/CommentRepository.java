package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
	Comment save(Comment comment);

	Optional<Comment> findById(Long id);

	List<Comment> findByPostId(Long postId);
}

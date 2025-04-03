package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByIdAndDeletedAtIsNull(Long id);

	List<Comment> findByPostIdAndDeletedAtIsNull(Long postId);
}

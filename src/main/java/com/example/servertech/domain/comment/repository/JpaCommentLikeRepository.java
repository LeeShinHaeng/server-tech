package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentLikeRepository extends JpaRepository<CommentLike, Long> {
	void deleteByCommentIdAndLikerId(Long commentId, Long likerId);

	List<CommentLike> findAllByCommentIdInAndLikerId(List<Long> commentIds, Long likerId);

	Integer countByCommentId(Long commentId);
}

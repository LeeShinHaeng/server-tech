package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {
	private final JpaCommentLikeRepository jpaRepository;

	@Override
	public CommentLike save(CommentLike commentLike) {
		return jpaRepository.save(commentLike);
	}

	@Override
	public void deleteByCommentAndUser(Long commentId, Long userId) {
		jpaRepository.deleteByCommentIdAndLikerId(commentId, userId);
	}

	@Override
	public Optional<CommentLike> findByCommentIdAndLikerId(Long commentId, Long userId) {
		return jpaRepository.findByCommentIdAndLikerId(commentId, userId);
	}
}

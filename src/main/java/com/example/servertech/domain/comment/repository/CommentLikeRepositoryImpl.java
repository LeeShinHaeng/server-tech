package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	public List<CommentLike> findAllByCommentIdInAndLikerId(List<Long> commentIds, Long likerId) {
		return jpaRepository.findAllByCommentIdInAndLikerId(commentIds, likerId);
	}

	@Override
	public Integer countByCommentId(Long commentId) {
		return jpaRepository.countByCommentId(commentId);
	}
}

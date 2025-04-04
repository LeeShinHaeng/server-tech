package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {
	private final JpaCommentLikeRepository jpaRepository;

	@Override
	public CommentLike save(CommentLike commentLike) {
		return jpaRepository.save(commentLike);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}

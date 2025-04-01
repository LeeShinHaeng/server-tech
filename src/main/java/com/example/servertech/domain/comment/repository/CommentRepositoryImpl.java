package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
	private final JpaCommentRepository jpaRepository;

	@Override
	public Comment save(Comment comment) {
		return jpaRepository.save(comment);
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return jpaRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}

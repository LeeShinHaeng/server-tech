package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepository {
	private final JpaPostLikeRepository jpaRepository;

	@Override
	public PostLike save(PostLike postLike) {
		return jpaRepository.save(postLike);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}

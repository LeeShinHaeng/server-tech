package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
	private final JpaPostRepository jpaRepository;

	@Override
	public Post save(Post post) {
		return jpaRepository.save(post);
	}

	@Override
	public Optional<Post> findById(Long id) {
		return jpaRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}
}

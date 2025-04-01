package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.Post;

import java.util.Optional;

public interface PostRepository {
	Post save(Post post);

	Optional<Post> findById(Long id);

	void deleteById(Long id);
}

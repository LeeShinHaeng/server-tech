package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
	Post save(Post post);

	List<Post> findAll();

	Optional<Post> findById(Long id);
}

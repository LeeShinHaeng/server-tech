package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByDeletedAtIsNull();

	Optional<Post> findByIdAndDeletedAtIsNull(Long id);
}

package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<Post, Long> {
}

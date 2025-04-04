package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostLikeRepository extends JpaRepository<PostLike, Long> {
}

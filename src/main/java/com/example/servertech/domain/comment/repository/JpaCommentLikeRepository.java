package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentLikeRepository extends JpaRepository<CommentLike, Long> {
}

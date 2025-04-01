package com.example.servertech.domain.comment.repository;

import com.example.servertech.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
}

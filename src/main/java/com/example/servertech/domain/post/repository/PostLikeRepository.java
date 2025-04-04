package com.example.servertech.domain.post.repository;

import com.example.servertech.domain.post.entity.PostLike;

public interface PostLikeRepository {
	PostLike save(PostLike postLike);
	void deleteById(Long id);
}

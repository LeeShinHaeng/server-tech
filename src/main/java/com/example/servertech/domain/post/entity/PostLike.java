package com.example.servertech.domain.post.entity;

import com.example.servertech.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@Table(name = "post_like")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class PostLike{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "post_id", nullable = false, updatable = false)
	private Post post;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "liker_id", nullable = false, updatable = false)
	private User liker;

	public static PostLike create(Post post, User liker) {
		return PostLike.builder()
			.post(post)
			.liker(liker)
			.build();
	}
}

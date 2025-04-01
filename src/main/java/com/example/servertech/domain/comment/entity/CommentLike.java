package com.example.servertech.domain.comment.entity;

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
@Table(name = "comment_like")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class CommentLike {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "comment_id", nullable = false, updatable = false)
	private Comment comment;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "liker_id", nullable = false, updatable = false)
	private User liker;

	public static CommentLike create(Comment comment, User liker) {
		return CommentLike.builder()
			.comment(comment)
			.liker(liker)
			.build();
	}
}

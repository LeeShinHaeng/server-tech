package com.example.servertech.domain.comment.entity;

import com.example.servertech.domain.common.entity.BaseTimeEntity;
import com.example.servertech.domain.post.entity.Post;
import com.example.servertech.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "post_id", nullable = false, updatable = false)
	private Post post;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "writer_id", nullable = false, updatable = false)
	private User writer;

	@Column(nullable = false)
	private String content;

	public static Comment create(Post post, User writer, String content) {
		return Comment.builder()
			.post(post)
			.writer(writer)
			.content(content)
			.build();
	}

	public void updateContent(String content) {
		this.content = content;
	}
}

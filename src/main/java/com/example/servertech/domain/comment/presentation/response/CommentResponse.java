package com.example.servertech.domain.comment.presentation.response;

import com.example.servertech.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record CommentResponse(
	@Schema(description = "댓글 아이디", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "작성자 이름", example = "이신행", requiredMode = REQUIRED)
	String writerName,

	@Schema(description = "댓글 내용", example = "어서오세요!", requiredMode = REQUIRED)
	String content,

	@Schema(description = "좋아요 여부", example = "true", requiredMode = REQUIRED)
	Boolean like
) {
	public static CommentResponse create(Comment comment, Boolean like) {
		return CommentResponse.builder()
			.id(comment.getId())
			.writerName(comment.getWriter().getName())
			.content(comment.getContent())
			.like(like)
			.build();
	}
}

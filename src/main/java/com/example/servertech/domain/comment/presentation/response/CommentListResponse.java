package com.example.servertech.domain.comment.presentation.response;

import com.example.servertech.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record CommentListResponse(
	@Schema(description = "댓글 리스트",
		example = "[{"
			+ "\"id\": 1, "
			+ "\"writerName\": \"이신행\", "
			+ "\"title\": \"어서오세요!\""
			+ "}]",
		requiredMode = REQUIRED)
	List<CommentResponse> commentResponseList,

	@Schema(description = "댓글 개수", example = "2", requiredMode = REQUIRED)
	Integer size
) {
	public static CommentListResponse create(Map<Comment, Boolean> commentMap) {
		return CommentListResponse.builder()
			.commentResponseList(
				commentMap
					.keySet()
					.stream()
					.map(comment -> CommentResponse.create(comment, commentMap.get(comment)))
					.toList()
			)
			.size(commentMap.size())
			.build();
	}
}

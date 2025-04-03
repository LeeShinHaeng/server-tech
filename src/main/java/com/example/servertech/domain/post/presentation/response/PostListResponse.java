package com.example.servertech.domain.post.presentation.response;

import com.example.servertech.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record PostListResponse(
	@Schema(description = "게시글 리스트",
		example = "[{"
			+ "\"id\": 1, "
			+ "\"title\": \"가입 인사\""
			+ "}]",
		requiredMode = REQUIRED)
	List<PostResponse> postResponseList,

	@Schema(description = "게시글 개수", example = "100", requiredMode = REQUIRED)
	Integer size
) {
	public static PostListResponse create(List<Post> postList) {
		return PostListResponse.builder()
			.postResponseList(
				postList.stream()
					.map(post -> PostResponse.create(post.getId(), post.getTitle()))
					.toList()
			)
			.size(postList.size())
			.build();
	}
}

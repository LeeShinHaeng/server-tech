package com.example.servertech.domain.post.presentation.response;

import com.example.servertech.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record PostDetailResponse(
	@Schema(description = "게시글 제목", example = "가입 인사", requiredMode = REQUIRED)
	String title,

	@Schema(description = "게시글 내용", example = "잘 부탁드립니닷!", requiredMode = REQUIRED)
	String contents
) {
	public static PostDetailResponse create(Post post) {
		return PostDetailResponse.builder()
			.title(post.getTitle())
			.contents(post.getContent())
			.build();
	}
}

package com.example.servertech.domain.post.presentation;

import com.example.servertech.domain.post.presentation.request.PostCreateRequest;
import com.example.servertech.domain.post.presentation.response.PostDetailResponse;
import com.example.servertech.domain.post.presentation.response.PostListResponse;
import com.example.servertech.domain.post.presentation.response.PostPersistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Post", description = "게시글 API")
public interface PostController {

	@Operation(summary = "게시글 작성 API", description = """
			- Description : 이 API는 새로운 게시글을 작성 합니다.
		""")
	@ApiResponse(
		responseCode = "201",
		content = @Content(schema = @Schema(implementation = PostPersistResponse.class))
	)
	ResponseEntity<PostPersistResponse> write(PostCreateRequest request);

	@Operation(summary = "게시글 전체 조회 API", description = """
			- Description : 이 API는 모든 게시글을 조회 합니다.
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = PostListResponse.class)))
	ResponseEntity<PostListResponse> getAll();

	@Operation(summary = "게시글 세부 조회 API", description = """
			- Description : 이 API는 게시글을 세부 조회 합니다.
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = PostDetailResponse.class))
	)
	ResponseEntity<PostDetailResponse> get(Long id);

	@Operation(summary = "게시글 수정 API", description = """
			- Description : 이 API는 게시글을 수정 합니다.
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> update(Long id, PostCreateRequest request);

	@Operation(summary = "게시글 삭제 API", description = """
			- Description : 이 API는 게시글을 삭제 합니다.
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(Long id);
}

package com.example.servertech.domain.comment.presentation;

import com.example.servertech.domain.comment.presentation.request.CommentCreateRequest;
import com.example.servertech.domain.comment.presentation.response.CommentDetailResponse;
import com.example.servertech.domain.comment.presentation.response.CommentPersistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Comment", description = "댓글 API")
public interface CommentController {

	@Operation(summary = "댓글 작성 API", description = """
			- Description : 이 API는 새로운 댓글을 작성 합니다.
		""")
	@ApiResponse(
		responseCode = "201",
		content = @Content(schema = @Schema(implementation = CommentPersistResponse.class))
	)
	ResponseEntity<CommentPersistResponse> write(Long postId, CommentCreateRequest request);

	@Operation(summary = "게시글 별 댓글 조회 API", description = """
			- Description : 이 API는 게시글에 달린 댓글을 조회 합니다.
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = CommentDetailResponse.class))
	)
	ResponseEntity<CommentDetailResponse> getByPostId(Long postId);

	@Operation(summary = "댓글 수정 API", description = """
			- Description : 이 API는 댓글을 수정 합니다.
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> update(Long id, CommentCreateRequest request);

	@Operation(summary = "댓글 삭제 API", description = """
			- Description : 이 API는 댓글을 삭제 합니다.
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete(Long id);

}

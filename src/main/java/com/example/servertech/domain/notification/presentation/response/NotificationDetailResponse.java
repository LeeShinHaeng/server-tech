package com.example.servertech.domain.notification.presentation.response;

import com.example.servertech.domain.notification.entity.Notification;
import com.example.servertech.domain.notification.entity.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record NotificationDetailResponse(
	@Schema(description = "알림 제목", example = "게시글에 댓글이 달렸습니다.", requiredMode = REQUIRED)
	String title,
	@Schema(description = "알림 내용", example = "{사용자명}이 {게시글제목}에 댓글을 달았습니다.", requiredMode = REQUIRED)
	String content,
	@Schema(description = "알림 타입", example = "POST_COMMENTED", requiredMode = REQUIRED)
	NotificationType type,
	@Schema(description = "알림 생성 시간", example = "2025-04-07T09:27:01.184843", requiredMode = REQUIRED)
	LocalDateTime createTime
) {
	public static NotificationDetailResponse create(Notification notification) {
		return NotificationDetailResponse.builder()
			.title(notification.getTitle())
			.content(notification.getContent())
			.type(notification.getType())
			.createTime(notification.getCreateTime())
			.build();
	}
}

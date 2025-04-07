package com.example.servertech.domain.notification.presentation.response;

import com.example.servertech.domain.notification.entity.Notification;
import com.example.servertech.domain.notification.entity.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record NotificationResponse(
	@Schema(description = "알림 아이디", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "알림 제목", example = "게시글에 댓글이 달렸습니다.", requiredMode = REQUIRED)
	String title,

	@Schema(description = "알림 타입", example = "POST_COMMENTED", requiredMode = REQUIRED)
	NotificationType type,

	@Schema(description = "알림 생성 시간", example = "2025-04-07T09:27:01.184843", requiredMode = REQUIRED)
	LocalDateTime createTime
) {
	public static NotificationResponse create(Notification notification) {
		return NotificationResponse.builder()
			.id(notification.getId())
			.title(notification.getTitle())
			.type(notification.getType())
			.createTime(notification.getCreateTime())
			.build();
	}
}

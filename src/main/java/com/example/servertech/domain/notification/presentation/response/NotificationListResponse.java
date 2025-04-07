package com.example.servertech.domain.notification.presentation.response;

import com.example.servertech.domain.notification.entity.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record NotificationListResponse(
	@Schema(description = "알림 리스트",
		example = "[{"
			+ "\"id\": 1, "
			+ "\"title\": \"게시글에 댓글이 달렸습니다.\", "
			+ "\"type\": \"POST_COMMENTED\", "
			+ "\"createTime\": \"2025-04-07T09:27:01.184843\""
			+ "}]",
		requiredMode = REQUIRED)
	List<NotificationResponse> notificationResponseList,

	@Schema(description = "알림 개수", example = "2", requiredMode = REQUIRED)
	Integer size
) {
	public static NotificationListResponse create(List<Notification> notificationList) {
		List<NotificationResponse> responseList = new ArrayList<>();
		notificationList.forEach(
			n -> responseList.add(NotificationResponse.create(n))
		);

		return NotificationListResponse.builder()
			.notificationResponseList(responseList)
			.size(notificationList.size())
			.build();
	}
}

package com.example.servertech.domain.notification.presentation.request;

import com.example.servertech.domain.notification.entity.NotificationType;
import lombok.Builder;

@Builder
public record NotificationRequest(
	String content,
	NotificationType type,
	Long receiverId
) {
	public static NotificationRequest create(String content, NotificationType type, Long receiverId) {
		return NotificationRequest.builder()
			.content(content)
			.type(type)
			.receiverId(receiverId)
			.build();
	}
}

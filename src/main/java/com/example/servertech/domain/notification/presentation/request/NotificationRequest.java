package com.example.servertech.domain.notification.presentation.request;

import com.example.servertech.domain.notification.entity.NotificationType;
import lombok.Builder;

@Builder
public record NotificationRequest(
	String title,
	String content,
	NotificationType type,
	Long receiverId
) {
	public static NotificationRequest create(String title, String content, NotificationType type, Long receiverId) {
		return NotificationRequest.builder()
			.title(title)
			.content(content)
			.type(type)
			.receiverId(receiverId)
			.build();
	}
}

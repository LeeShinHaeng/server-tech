package com.example.servertech.domain.notification.presentation.request;

import com.example.servertech.common.event.domain.EventType;
import lombok.Builder;

@Builder
public record NotificationRequest(
	String content,
	EventType type,
	Long receiverId
) {
	public static NotificationRequest create(String content, EventType type, Long receiverId) {
		return NotificationRequest.builder()
			.content(content)
			.type(type)
			.receiverId(receiverId)
			.build();
	}
}

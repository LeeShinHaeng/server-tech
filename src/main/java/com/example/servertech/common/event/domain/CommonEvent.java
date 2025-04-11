package com.example.servertech.common.event.domain;

import lombok.Builder;

@Builder
public record CommonEvent(
	Long senderId,
	Long targetId,
	EventType eventType
) {
	public static CommonEvent create(Long senderId, Long targetId, EventType eventType) {
		return CommonEvent.builder()
			.senderId(senderId)
			.targetId(targetId)
			.eventType(eventType)
			.build();
	}
}

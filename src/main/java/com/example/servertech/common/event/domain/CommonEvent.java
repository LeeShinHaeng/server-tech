package com.example.servertech.common.event.domain;

import lombok.Builder;

@Builder
public record CommonEvent(
	Long targetId,
	EventType eventType
) {
	public static CommonEvent create(Long targetId, EventType eventType) {
		return CommonEvent.builder()
			.targetId(targetId)
			.eventType(eventType)
			.build();
	}
}

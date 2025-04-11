package com.example.servertech.common.event.consumer;

import com.example.servertech.common.event.domain.CommonEvent;
import com.example.servertech.common.event.exception.InvalidMessageFormatException;
import com.example.servertech.common.event.properties.RedisProperties;
import com.example.servertech.domain.notification.application.NotificationService;
import com.example.servertech.domain.notification.presentation.request.NotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisStreamListener implements StreamListener<String, ObjectRecord<String, String>> {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private final RedisProperties redisProperties;
	private final NotificationService notificationService;

	@Override
	public void onMessage(ObjectRecord<String, String> message) {
		try {
			String record = String.valueOf(message.getId());
			CommonEvent event = objectMapper.readValue(message.getValue(), CommonEvent.class);
			NotificationRequest request = NotificationRequest.create(record, event.eventType(), event.senderId());
			notificationService.save(request);

			redisTemplate.opsForStream().trim(redisProperties.getStreamKey(), 1000);
		} catch (JsonProcessingException e) {
			throw new InvalidMessageFormatException();
		}
	}
}

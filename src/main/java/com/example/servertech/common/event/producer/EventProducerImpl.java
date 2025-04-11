package com.example.servertech.common.event.producer;

import com.example.servertech.common.event.domain.CommonEvent;
import com.example.servertech.common.event.exception.StreamProcessingException;
import com.example.servertech.common.event.properties.RedisProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {
	private final StringRedisTemplate redisTemplate;
	private final ObjectMapper objectMapper;
	private final RedisProperties properties;

	@Override
	public void publish(CommonEvent event) {
		try {
			String jsonValue = objectMapper.writeValueAsString(event);

			ObjectRecord<String, String> record = StreamRecords.newRecord()
				.in(properties.getSTREAM_KEY())
				.ofObject(jsonValue);
			RecordId recordId = redisTemplate.opsForStream().add(record);

			if (Optional.ofNullable(recordId).isEmpty()) {
				throw new StreamProcessingException();
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}

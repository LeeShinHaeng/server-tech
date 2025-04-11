package com.example.servertech.config;

import com.example.servertech.common.event.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RedisStreamConfig {
	private final RedisProperties redisProperties;

	@Bean
	public StreamMessageListenerContainer<String, ObjectRecord<String, String>> streamMessageListenerContainer(
		RedisConnectionFactory connectionFactory,
		StreamListener<String, ObjectRecord<String, String>> streamListener) {

		StreamMessageListenerContainer
			.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> containerOptions =
			StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
				.pollTimeout(Duration.ofSeconds(1))
				.targetType(String.class)
				.build();

		StreamMessageListenerContainer<String, ObjectRecord<String, String>> container =
			StreamMessageListenerContainer.create(connectionFactory, containerOptions);

		container.receive(
			StreamOffset.create(redisProperties.getStreamKey(), ReadOffset.lastConsumed()),
			streamListener
		);

		container.start();
		return container;
	}
}

package com.example.servertech.common.event.producer;

import com.example.servertech.common.event.domain.CommonEvent;

public interface EventProducer {
	void publish(CommonEvent event);
}

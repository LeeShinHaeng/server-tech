package com.example.servertech.mock.event;

import com.example.servertech.common.event.domain.CommonEvent;
import com.example.servertech.common.event.producer.EventProducer;

public class FakeEventProducer implements EventProducer {
	@Override
	public void publish(CommonEvent event) {

	}
}

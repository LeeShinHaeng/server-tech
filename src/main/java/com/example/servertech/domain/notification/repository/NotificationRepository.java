package com.example.servertech.domain.notification.repository;

import com.example.servertech.domain.notification.entity.Notification;

import java.util.Optional;

public interface NotificationRepository {
	Notification save(Notification notification);
	Optional<Notification> findById(Long id);
	void deleteById(Long id);
}

package com.example.servertech.domain.notification.repository;

import com.example.servertech.domain.notification.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
	Notification save(Notification notification);
	Optional<Notification> findById(Long id);
	List<Notification> findByReceiverId(Long receiverId);
}

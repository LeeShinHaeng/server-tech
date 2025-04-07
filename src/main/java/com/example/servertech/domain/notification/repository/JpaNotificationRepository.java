package com.example.servertech.domain.notification.repository;

import com.example.servertech.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaNotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByReceiverId(Long userId);
}

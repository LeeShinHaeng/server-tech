package com.example.servertech.domain.notification.repository;

import com.example.servertech.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<Notification, Long> {
}

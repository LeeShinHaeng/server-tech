package com.example.servertech.domain.notification.repository;

import com.example.servertech.domain.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
	private final JpaNotificationRepository jpaRepository;

	@Override
	public Notification save(Notification notification) {
		return jpaRepository.save(notification);
	}

	@Override
	public Optional<Notification> findById(Long id) {
		return jpaRepository.findById(id);
	}

	@Override
	public List<Notification> findByReceiverId(Long receiverId) {
		return jpaRepository.findByReceiverId(receiverId);
	}
}

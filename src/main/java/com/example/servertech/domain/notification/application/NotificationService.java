package com.example.servertech.domain.notification.application;

import com.example.servertech.domain.notification.dto.NotificationRequest;
import com.example.servertech.domain.notification.entity.Notification;
import com.example.servertech.domain.notification.repository.NotificationRepository;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final UserService userService;
	private final NotificationRepository notificationRepository;

	// 저장
	@Transactional
	public Notification save(NotificationRequest request) {
		User receiver = userService.getUserById(request.receiverId());

		return notificationRepository.save(Notification.create(
			request.title(), request.content(), request.type(), receiver
		));
	}

	// 내 알림 전체 조회

	// 알림 새부 내용 조회
}

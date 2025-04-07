package com.example.servertech.domain.notification.application;

import com.example.servertech.domain.notification.presentation.request.NotificationRequest;
import com.example.servertech.domain.notification.presentation.response.NotificationListResponse;
import com.example.servertech.domain.notification.entity.Notification;
import com.example.servertech.domain.notification.repository.NotificationRepository;
import com.example.servertech.domain.user.application.UserService;
import com.example.servertech.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final UserService userService;
	private final NotificationRepository notificationRepository;

	@Transactional
	public Notification save(NotificationRequest request) {
		User receiver = userService.getUserById(request.receiverId());

		return notificationRepository.save(Notification.create(
			request.title(), request.content(), request.type(), receiver
		));
	}

	// 내 알림 전체 조회
	@Transactional(readOnly = true)
	public NotificationListResponse findByLoginUser() {
		Optional<User> user = userService.getAuthenticatedUser();
		if(user.isPresent()){
			List<Notification> notificationList = notificationRepository.findByReceiverId(user.get().getId());
			return NotificationListResponse.create(notificationList);
		}
		return NotificationListResponse.create(new ArrayList<>());
	}

	// 알림 새부 내용 조회 -> 읽음 처리
}

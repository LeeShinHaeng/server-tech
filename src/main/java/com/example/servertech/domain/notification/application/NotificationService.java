package com.example.servertech.domain.notification.application;

import com.example.servertech.domain.notification.exception.NoSuchNotificationException;
import com.example.servertech.domain.notification.presentation.request.NotificationRequest;
import com.example.servertech.domain.notification.presentation.response.NotificationDetailResponse;
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

	// todo 생성시 로직 변경
	@Transactional
	public Notification save(NotificationRequest request) {
		User receiver = userService.getUserById(request.receiverId());

		return notificationRepository.save(Notification.create(
			request.title(), request.content(), request.type(), receiver
		));
	}

	@Transactional(readOnly = true)
	public NotificationListResponse findByLoginUser() {
		Optional<User> user = userService.getAuthenticatedUser();
		if(user.isPresent()){
			List<Notification> notificationList = notificationRepository.findByReceiverId(user.get().getId());
			return NotificationListResponse.create(notificationList);
		}
		return NotificationListResponse.create(new ArrayList<>());
	}

	@Transactional
	public NotificationDetailResponse findById(Long id) {
		Notification notification = notificationRepository.findById(id)
			.orElseThrow(NoSuchNotificationException::new);

		notification.read();
		return NotificationDetailResponse.create(notification);
	}
}

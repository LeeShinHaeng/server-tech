package com.example.servertech.domain.notification.presentation;

import com.example.servertech.domain.notification.application.NotificationService;
import com.example.servertech.domain.notification.presentation.request.NotificationRequest;
import com.example.servertech.domain.notification.presentation.response.NotificationListResponse;
import com.example.servertech.domain.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationControllerImpl implements NotificationController {
	private final NotificationService notificationService;

	@Override
	@PostMapping("/send")
	public ResponseEntity<Notification> send(NotificationRequest request) {
		Notification save = notificationService.save(request);
		return ResponseEntity.status(CREATED).body(save);
	}

	@Override
	@PostMapping("/me")
	public ResponseEntity<NotificationListResponse> findAll() {
		NotificationListResponse response = notificationService.findByLoginUser();
		return ResponseEntity.ok(response);
	}
}

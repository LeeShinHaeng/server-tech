package com.example.servertech.domain.notification.presentation;

import com.example.servertech.domain.notification.application.NotificationService;
import com.example.servertech.domain.notification.entity.Notification;
import com.example.servertech.domain.notification.presentation.request.NotificationRequest;
import com.example.servertech.domain.notification.presentation.response.NotificationDetailResponse;
import com.example.servertech.domain.notification.presentation.response.NotificationListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/me")
	public ResponseEntity<NotificationListResponse> findAll() {
		NotificationListResponse response = notificationService.findByLoginUser();
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<NotificationDetailResponse> findById(@PathVariable Long id) {
		NotificationDetailResponse response = notificationService.findById(id);
		return ResponseEntity.ok(response);
	}
}

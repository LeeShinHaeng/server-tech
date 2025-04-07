package com.example.servertech.domain.notification.presentation;

import com.example.servertech.domain.notification.dto.NotificationRequest;
import com.example.servertech.domain.notification.entity.Notification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Notification", description = "알림 테스트 API")
public interface NotificationController {

	@Operation(summary = "알림 저장 API", description = """
			- Description : 이 API는 새로운 알림을 저장 합니다. (별도의 Response 객체를 만들지 않았습니다.)
		""")
	@ApiResponse(
		responseCode = "201",
		content = @Content(schema = @Schema(implementation = Notification.class))
	)
	ResponseEntity<Notification> send(NotificationRequest request);
}

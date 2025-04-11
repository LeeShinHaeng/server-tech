package com.example.servertech.domain.notification.entity;


import com.example.servertech.common.event.domain.EventType;
import com.example.servertech.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Notification {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Enumerated(value = STRING)
	@Column(nullable = false, updatable = false)
	private EventType type;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "receiver_id", nullable = false, updatable = false)
	private User receiver;

	@Column(nullable = false)
	private Boolean isRead;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createTime;

	public static Notification create(String title, String content, EventType type, User receiver) {
		return Notification.builder()
			.title(title)
			.content(content)
			.type(type)
			.receiver(receiver)
			.isRead(false)
			.build();
	}

	public void read(){
		this.isRead = true;
	}
}

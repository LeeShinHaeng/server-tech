package com.example.servertech.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
	ADMIN("관리자"),
	NORMAL("일반사용자"),
	;

	private final String description;
}

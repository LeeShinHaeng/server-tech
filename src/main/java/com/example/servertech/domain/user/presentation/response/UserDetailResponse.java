package com.example.servertech.domain.user.presentation.response;

import com.example.servertech.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserDetailResponse(
	Long id,
	String name,
	String email,
	String role
) {
	public static UserDetailResponse create(User user) {
		return UserDetailResponse.builder()
			.id(user.getId())
			.name(user.getName())
			.email(user.getEmail())
			.role(user.getRole().getDescription())
			.build();
	}
}

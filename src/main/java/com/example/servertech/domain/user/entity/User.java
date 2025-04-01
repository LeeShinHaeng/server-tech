package com.example.servertech.domain.user.entity;

import com.example.servertech.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.servertech.domain.user.entity.UserRole.ADMIN;
import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@Table(name = "\"user\"")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private UserRole role;

	public static User createNormal(String name, String email, String password) {
		return User.builder()
			.name(name)
			.email(email)
			.password(password)
			.role(NORMAL)
			.build();
	}

	public static User createAdmin(String name, String email, String password) {
		return User.builder()
			.name(name)
			.email(email)
			.password(password)
			.role(ADMIN)
			.build();
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateEmail(String email) {
		this.email = email;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public boolean isPasswordMatch(String password, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, password);
	}
}

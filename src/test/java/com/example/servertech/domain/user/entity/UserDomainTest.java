package com.example.servertech.domain.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

import static com.example.servertech.domain.user.entity.UserRole.ADMIN;
import static com.example.servertech.domain.user.entity.UserRole.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDomainTest {
	private User user;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private final String NAME = "이신행";
	private final String EMAIL = "user@example.com";
	private final String RAW_PASSWORD = "password";
	private final String ENCODED_PASSWORD = encoder.encode(RAW_PASSWORD);

	@BeforeEach
	void init() {
		user = User.builder()
			.id(1L)
			.name(NAME)
			.email(EMAIL)
			.password(ENCODED_PASSWORD)
			.role(NORMAL)
			.build();
	}

	@Test
	@DisplayName("createNormal 은 새로운 User 객체를 생성합니다.")
	void createNormal_Success(){
		// when
		User newUser = User.createNormal(NAME, EMAIL, ENCODED_PASSWORD);

		// then
		assertNotNull(newUser);
		assertEquals(NAME, newUser.getName());
		assertEquals(EMAIL, newUser.getEmail());
		assertEquals(ENCODED_PASSWORD, newUser.getPassword());
		assertEquals(NORMAL, newUser.getRole());
	}

	@Test
	@DisplayName("createAdmin 은 ADMIN 역할의 User 객체를 생성합니다.")
	void createAdmin_Success(){
		// when
		User newUser = User.createAdmin(NAME, EMAIL, ENCODED_PASSWORD);

		//then
		assertNotNull(newUser);
		assertEquals(NAME, newUser.getName());
		assertEquals(EMAIL, newUser.getEmail());
		assertEquals(ENCODED_PASSWORD, newUser.getPassword());
		assertEquals(ADMIN, newUser.getRole());
	}

	@Test
	@DisplayName("updateName 은 User의 이름을 변경합니다.")
	void updateName_Success(){
		//given
		String newName = "홍길동";

		// when
		user.updateName(newName);

		//then
		assertEquals(newName, user.getName());
	}

	@Test
	@DisplayName("updateEmail 은 User의 이메일을 변경합니다.")
	void updateEmail_Success(){
		//given
		String newEmail = "new@example.com";

		// when
		user.updateEmail(newEmail);

		//then
		assertEquals(newEmail, user.getEmail());
	}

	@Test
	@DisplayName("updatePassword 은 User의 비밀번호를 변경합니다.")
	void updatePassword_Success(){
		//given
		String newPassword = encoder.encode("new-password");

		// when
		user.updatePassword(newPassword);

		//then
		assertEquals(newPassword, user.getPassword());
	}

	@Test
	@DisplayName("isPasswordMatch 은 User의 비밀번호가 맞으면 true를 반환합니다.")
	void isPasswordMatch_True(){
		// when
		boolean isMatch = user.isPasswordMatch("password", encoder);

		//then
		assertTrue(isMatch);
	}

	@Test
	@DisplayName("isPasswordMatch 은 User의 비밀번호가 아니면 false를 반환합니다.")
	void isPasswordMatch_False(){
		//given
		String newPassword = "not-password";

		// when
		boolean isMatch = user.isPasswordMatch(newPassword, encoder);

		//then
		assertFalse(isMatch);
	}

	@Test
	@DisplayName("getAuthorities 은 User의 role을 GrantedAuthority로 반환합니다.")
	void getAuthorities_Success(){
		// when
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities().stream().toList();

		//then
		assertNotNull(authorities);
		assertTrue(authorities.contains(new SimpleGrantedAuthority(NORMAL.name())));
	}

	@Test
	@DisplayName("getUsername 은 User의 id를 반환합니다.")
	void getUsername_Success(){
		// when
		String username = user.getUsername();

		//then
		assertNotNull(username);
		assertEquals("1", username);
	}
}
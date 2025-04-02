package com.example.servertech.domain.user.repository;

import com.example.servertech.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
	private final JpaUserRepository jpaRepository;

	@Override
	public User save(User user) {
		return jpaRepository.save(user);
	}

	@Override
	public Optional<User> findById(Long id) {
		return jpaRepository.findById(id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return jpaRepository.findByEmail(email);
	}
}

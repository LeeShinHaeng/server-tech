package com.example.servertech.domain.user.repository;

import com.example.servertech.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {

	User save(User user);

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);
}

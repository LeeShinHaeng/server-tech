package com.example.servertech.domain.user.repository;

import com.example.servertech.domain.user.entity.User;
import jakarta.validation.constraints.Pattern;

import java.util.Optional;

public interface UserRepository {

	User save(User user);

	Optional<User> findById(Long id);

	void deleteById(Long id);

	Optional<User> findByEmail(String email);
}

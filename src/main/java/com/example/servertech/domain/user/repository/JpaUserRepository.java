package com.example.servertech.domain.user.repository;

import com.example.servertech.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmailAndDeletedAtIsNull(String email);
	Optional<User> findByIdAndDeletedAtIsNull(Long id);
}

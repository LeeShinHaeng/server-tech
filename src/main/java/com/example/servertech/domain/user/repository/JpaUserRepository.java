package com.example.servertech.domain.user.repository;

import com.example.servertech.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {
}

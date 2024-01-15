package com.anant.SpringSecurity_2X.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anant.SpringSecurity_2X.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String username);
}

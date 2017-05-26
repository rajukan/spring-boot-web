package com.org.vg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.vg.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
}

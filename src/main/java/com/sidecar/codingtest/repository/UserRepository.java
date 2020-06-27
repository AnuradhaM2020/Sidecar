package com.sidecar.codingtest.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sidecar.codingtest.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Serializable>{
	UserEntity findByUsername(String username);
}

package com.test.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.identity_service.entity.InvalidatedToken;

public interface InvalidateRepository extends JpaRepository<InvalidatedToken, String> {}

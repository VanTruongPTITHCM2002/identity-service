package com.test.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.identity_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {}

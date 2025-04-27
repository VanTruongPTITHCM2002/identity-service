package com.test.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.identity_service.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {}

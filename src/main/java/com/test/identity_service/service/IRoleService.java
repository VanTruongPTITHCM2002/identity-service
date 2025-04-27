package com.test.identity_service.service;

import java.util.List;

import com.test.identity_service.dto.request.RoleRequest;
import com.test.identity_service.dto.response.RoleResponse;

public interface IRoleService {
    RoleResponse create(RoleRequest roleRequest);

    List<RoleResponse> getAll();

    void delete(String roleName);
}

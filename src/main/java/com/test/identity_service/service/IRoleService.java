package com.test.identity_service.service;

import com.test.identity_service.dto.request.RoleRequest;
import com.test.identity_service.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create (RoleRequest roleRequest);
    List<RoleResponse> getAll();
    void delete(String roleName);
}

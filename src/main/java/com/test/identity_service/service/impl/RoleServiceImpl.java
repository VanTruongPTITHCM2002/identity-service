package com.test.identity_service.service.impl;


import com.test.identity_service.dto.request.RoleRequest;
import com.test.identity_service.dto.response.RoleResponse;
import com.test.identity_service.mapper.RoleMapper;
import com.test.identity_service.repository.PermissionRepository;
import com.test.identity_service.repository.RoleRepository;
import com.test.identity_service.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleServiceImpl implements IRoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions = this.permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = this.roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        return this.roleRepository.findAll()
                .stream().map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public void delete(String roleName) {
        this.roleRepository.deleteById(roleName);
    }
}

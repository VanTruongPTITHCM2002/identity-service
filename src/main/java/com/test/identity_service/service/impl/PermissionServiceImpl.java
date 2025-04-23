package com.test.identity_service.service.impl;

import com.test.identity_service.dto.request.PermissionRequest;
import com.test.identity_service.dto.response.PermissionResponse;
import com.test.identity_service.entity.Permission;
import com.test.identity_service.mapper.PermissionMapper;
import com.test.identity_service.repository.PermissionRepository;
import com.test.identity_service.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionServiceImpl implements IPermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        var permissions = this.permissionRepository.findAll()
                .stream().map(permissionMapper::toPermissionResponse)
                .toList();
        return permissions;
    }

    @Override
    public void delete(String permission) {
        this.permissionRepository.deleteById(permission);
    }
}

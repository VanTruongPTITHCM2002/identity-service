package com.test.identity_service.mapper;

import com.test.identity_service.dto.request.PermissionRequest;
import com.test.identity_service.dto.response.PermissionResponse;
import com.test.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission (PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}

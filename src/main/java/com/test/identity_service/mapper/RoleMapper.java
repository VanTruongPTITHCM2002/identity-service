package com.test.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.test.identity_service.dto.request.RoleRequest;
import com.test.identity_service.dto.response.RoleResponse;
import com.test.identity_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}

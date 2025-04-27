package com.test.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.request.UserUpdateRequest;
import com.test.identity_service.dto.response.UserResponse;
import com.test.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}

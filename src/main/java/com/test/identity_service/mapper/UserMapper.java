package com.test.identity_service.mapper;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.response.UserResponse;
import com.test.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest userCreationRequest);
    @Mapping(target = "password", source = "password", ignore = true)
    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserCreationRequest userCreationRequest);
}

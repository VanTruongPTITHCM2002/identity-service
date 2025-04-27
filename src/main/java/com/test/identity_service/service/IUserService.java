package com.test.identity_service.service;

import java.util.List;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.request.UserUpdateRequest;
import com.test.identity_service.dto.response.UserResponse;

public interface IUserService {
    UserResponse addUser(UserCreationRequest userCreationRequest);

    List<UserResponse> getUsers();

    UserResponse getUser(String id);

    UserResponse updateUser(String id, UserUpdateRequest userUpdateRequest);

    UserResponse deleteUser(String id);
}

package com.test.identity_service.service;


import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.response.UserResponse;
import com.test.identity_service.entity.User;

import java.util.List;

public interface IUserService {
    UserResponse addUser(UserCreationRequest userCreationRequest);
    List<UserResponse> getUsers();
    UserResponse getUser(String id);
    UserResponse updateUser (String id, UserCreationRequest userCreationRequest);
    UserResponse deleteUser (String id);
}

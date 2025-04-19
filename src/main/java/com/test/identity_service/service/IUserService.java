package com.test.identity_service.service;


import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.entity.User;

import java.util.List;

public interface IUserService {
    User addUser(UserCreationRequest userCreationRequest);
    List<User> getUsers();
    User getUser(String id);
    User updateUser (String id, UserCreationRequest userCreationRequest);
    User deleteUser (String id);
}

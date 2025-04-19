package com.test.identity_service.service.impl;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.entity.User;
import com.test.identity_service.exception.AppException;
import com.test.identity_service.exception.ErrorCode;
import com.test.identity_service.repository.UserRepository;
import com.test.identity_service.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;

    @Override
    public User addUser(UserCreationRequest userCreationRequest) {

       boolean isExistsUsername = this.userRepository.existsByUsername(userCreationRequest.getUsername());

       if(isExistsUsername){
           throw new AppException(ErrorCode.USER_EXISTED);
       }

        User user = new User();
        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(userCreationRequest.getPassword());
        user.setFirstName(userCreationRequest.getFirstName());
        user.setLastName(userCreationRequest.getLastName());
        user.setDob(userCreationRequest.getDob());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(String id, UserCreationRequest userCreationRequest) {
        User user = getUser(id);
        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(userCreationRequest.getPassword());
        user.setFirstName(userCreationRequest.getFirstName());
        user.setLastName(userCreationRequest.getLastName());
        user.setDob(userCreationRequest.getDob());
        userRepository.save(user);
        return user;
    }

    @Override
    public User deleteUser(String id) {
        User user = getUser(id);
        this.userRepository.delete(user);
        return user;
    }
}

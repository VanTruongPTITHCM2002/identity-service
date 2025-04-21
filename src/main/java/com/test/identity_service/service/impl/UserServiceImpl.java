package com.test.identity_service.service.impl;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.response.UserResponse;
import com.test.identity_service.entity.User;
import com.test.identity_service.enums.Role;
import com.test.identity_service.exception.AppException;
import com.test.identity_service.exception.ErrorCode;
import com.test.identity_service.mapper.UserMapper;
import com.test.identity_service.repository.UserRepository;
import com.test.identity_service.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Override
    public UserResponse addUser(UserCreationRequest userCreationRequest) {

       boolean isExistsUsername = this.userRepository.existsByUsername(userCreationRequest.getUsername());

       if(isExistsUsername){
           throw new AppException(ErrorCode.USER_EXISTED);
       }

       HashSet<String> roles = new HashSet<>();
       roles.add(Role.USER.name());

       User user = userMapper.toUser(userCreationRequest);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setRoles(roles);
       userRepository.save(user);
       return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found")));
    }

    @Override
    public UserResponse updateUser(String id, UserCreationRequest userCreationRequest) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        userMapper.updateUser(user,userCreationRequest);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        this.userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }
}

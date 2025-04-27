package com.test.identity_service.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.request.UserUpdateRequest;
import com.test.identity_service.dto.response.UserResponse;
import com.test.identity_service.entity.Role;
import com.test.identity_service.entity.User;
import com.test.identity_service.exception.AppException;
import com.test.identity_service.exception.ErrorCode;
import com.test.identity_service.mapper.UserMapper;
import com.test.identity_service.repository.RoleRepository;
import com.test.identity_service.repository.UserRepository;
import com.test.identity_service.service.IUserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    RoleRepository roleRepository;

    @Override
    public UserResponse addUser(UserCreationRequest userCreationRequest) {

        log.info("USER SERVICE");


        HashSet<Role> roles = new HashSet<>();
        var role = this.roleRepository.findById("USER").orElse(null);
        roles.add(role);

        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        try{
            this.userRepository.save(user);
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        return this.userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, userUpdateRequest);
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        var roles = this.roleRepository.findAllById(userUpdateRequest.getRoles());

        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        this.userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }
}

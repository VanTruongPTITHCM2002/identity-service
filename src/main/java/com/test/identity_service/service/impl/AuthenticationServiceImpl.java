package com.test.identity_service.service.impl;

import com.test.identity_service.dto.request.AuthenticationRequest;
import com.test.identity_service.exception.AppException;
import com.test.identity_service.exception.ErrorCode;
import com.test.identity_service.repository.UserRepository;
import com.test.identity_service.service.IAuthentcationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationServiceImpl implements IAuthentcationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticate(AuthenticationRequest authenticationRequest) {
        var user = this.userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        System.out.println(user.getUsername());
        return passwordEncoder.matches( authenticationRequest.getPassword(), user.getPassword());
    }
}

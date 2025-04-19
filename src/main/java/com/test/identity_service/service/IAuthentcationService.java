package com.test.identity_service.service;

import com.test.identity_service.dto.request.AuthenticationRequest;

public interface IAuthentcationService {
    boolean authenticate(AuthenticationRequest authenticationRequest);
}

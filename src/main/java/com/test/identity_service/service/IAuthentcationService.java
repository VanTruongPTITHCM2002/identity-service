package com.test.identity_service.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.test.identity_service.dto.request.AuthenticationRequest;
import com.test.identity_service.dto.request.InstropectRequest;
import com.test.identity_service.dto.request.InvalidTokenRequest;
import com.test.identity_service.dto.request.RefreshRequest;
import com.test.identity_service.dto.response.AuthenticationResponse;
import com.test.identity_service.dto.response.IntrospectResponse;

public interface IAuthentcationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws JOSEException;

    IntrospectResponse introspect(InstropectRequest instropectRequest) throws JOSEException, ParseException;

    void logout(InvalidTokenRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}

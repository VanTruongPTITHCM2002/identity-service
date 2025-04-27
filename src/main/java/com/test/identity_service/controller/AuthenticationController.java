package com.test.identity_service.controller;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.test.identity_service.dto.request.AuthenticationRequest;
import com.test.identity_service.dto.request.InstropectRequest;
import com.test.identity_service.dto.request.InvalidTokenRequest;
import com.test.identity_service.dto.request.RefreshRequest;
import com.test.identity_service.dto.response.ApiResponse;
import com.test.identity_service.dto.response.AuthenticationResponse;
import com.test.identity_service.dto.response.IntrospectResponse;
import com.test.identity_service.service.IAuthentcationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    IAuthentcationService iAuthentcationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) throws JOSEException {
        AuthenticationResponse authenticationResponse = this.iAuthentcationService.authenticate(authenticationRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .data(authenticationResponse)
                        .status(1000)
                        .build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> validToken(@RequestBody InstropectRequest instropectRequest)
            throws ParseException, JOSEException {
        var result = this.iAuthentcationService.introspect(instropectRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.<IntrospectResponse>builder().data(result).build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody InvalidTokenRequest invalidTokenRequest)
            throws ParseException, JOSEException {
        this.iAuthentcationService.logout(invalidTokenRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Sign out successfully")
                        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        AuthenticationResponse authenticationResponse = this.iAuthentcationService.refreshToken(request);
        return ResponseEntity.ok()
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .data(authenticationResponse)
                        .status(1000)
                        .build());
    }
}

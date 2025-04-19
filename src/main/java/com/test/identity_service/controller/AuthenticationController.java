package com.test.identity_service.controller;

import com.nimbusds.jose.JOSEException;
import com.test.identity_service.dto.request.AuthenticationRequest;
import com.test.identity_service.dto.request.InstropectRequest;
import com.test.identity_service.dto.response.ApiResponse;
import com.test.identity_service.dto.response.AuthenticationResponse;
import com.test.identity_service.dto.response.IntrospectResponse;
import com.test.identity_service.service.IAuthentcationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    IAuthentcationService iAuthentcationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws JOSEException {
        AuthenticationResponse authenticationResponse = this.iAuthentcationService.authenticate(authenticationRequest);
        return ResponseEntity.ok().body(ApiResponse.<AuthenticationResponse>builder()
                        .data(authenticationResponse)
                        .status(1000)
                .build());
    }

    @PostMapping("/introspect")
    public  ResponseEntity<ApiResponse<IntrospectResponse>> validToken (@RequestBody InstropectRequest instropectRequest) throws ParseException, JOSEException {
        var result = this.iAuthentcationService.introspect(instropectRequest);
        return  ResponseEntity.ok().body(
                ApiResponse.<IntrospectResponse>builder()
                        .data(result)
                        .build()
        );
    }
}

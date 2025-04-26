package com.test.identity_service.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.test.identity_service.dto.request.AuthenticationRequest;
import com.test.identity_service.dto.request.InstropectRequest;
import com.test.identity_service.dto.request.InvalidTokenRequest;
import com.test.identity_service.dto.request.RefreshRequest;
import com.test.identity_service.dto.response.AuthenticationResponse;
import com.test.identity_service.dto.response.IntrospectResponse;
import com.test.identity_service.entity.InvalidatedToken;
import com.test.identity_service.entity.User;
import com.test.identity_service.exception.AppException;
import com.test.identity_service.exception.ErrorCode;
import com.test.identity_service.repository.InvalidateRepository;
import com.test.identity_service.repository.UserRepository;
import com.test.identity_service.service.IAuthentcationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationServiceImpl implements IAuthentcationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    InvalidateRepository invalidateRepository;

    @NonFinal
    @Value("${SECRET_KEY}")
    protected String SECRET_KEY ;

    @NonFinal
    @Value("${VALID_DURATION}")
    private int validDuration;

    @NonFinal
    @Value("${REFRESHABLE_DURATION}")
    private int refreshAbleDuration;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws JOSEException {
        var user = this.userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated =  passwordEncoder.matches( authenticationRequest.getPassword(), user.getPassword());
        System.out.println(user.getRoles());
        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    @Override
    public IntrospectResponse introspect(InstropectRequest instropectRequest) throws JOSEException, ParseException {
        var token = instropectRequest.getToken();
        boolean isValid = true;
        try{
            verifyToken(token,false);
        }catch (AppException appException){
               isValid = false;
        }
        return  IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public void logout(InvalidTokenRequest request) throws ParseException, JOSEException {
       try{
           var signJwtToken = verifyToken(request.getToken(),true);
           String jit = signJwtToken.getJWTClaimsSet().getJWTID();
           Date expiredTime = signJwtToken.getJWTClaimsSet().getExpirationTime();

           InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                   .id(jit)
                   .expiryTime(expiredTime)
                   .build();

           invalidateRepository.save(invalidatedToken);
       }catch (AppException appException){

       }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken(),true);

        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiredTime = signJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiredTime)
                .build();

        invalidateRepository.save(invalidatedToken);

        var username = signJWT.getJWTClaimsSet().getSubject();

        var user = this.userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED)
        );

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    private SignedJWT verifyToken (String token,boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date verifyTime = isRefresh ?
         new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(refreshAbleDuration,ChronoUnit.SECONDS).toEpochMilli())
        :signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified =  signedJWT.verify(verifier);

        if(!(verified && verifyTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidateRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private String generateToken(User user) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("jod.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);

        jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
        return jwsObject.serialize();
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }

            });
        }
        return stringJoiner.toString();
    }
}

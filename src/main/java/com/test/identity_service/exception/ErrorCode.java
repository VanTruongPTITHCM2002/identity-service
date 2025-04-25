package com.test.identity_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1002, "Invalid Message Key",HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001,"User existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "User must be at least {min} characters",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004,"Password must be at least {min} characters",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005,"User not existed",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006,"UNAUTHENTICATED",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"You don't have permission",HttpStatus.FORBIDDEN),
    INVALID_DOB(1008,"Your age must be at least {min}",HttpStatus.BAD_REQUEST)
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}

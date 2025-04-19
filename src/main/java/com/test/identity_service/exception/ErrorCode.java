package com.test.identity_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized Exception"),
    INVALID_KEY(1002, "Invalid Message Key"),
    USER_EXISTED(1001,"User existed"),
    USERNAME_INVALID(1003, "User must be at least 3 characters"),
    INVALID_PASSWORD(1004,"Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005,"User not existed"),
    UNAUTHENTICATED(1006,"UNAUTHENTICATED")
    ;
    private final int code;
    private final String message;
}

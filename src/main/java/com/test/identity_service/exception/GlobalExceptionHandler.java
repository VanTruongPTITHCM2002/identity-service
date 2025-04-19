package com.test.identity_service.exception;

import com.test.identity_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ApiResponse<String>> handleRuntimeException (RuntimeException re){
        return  ResponseEntity.badRequest().body(
               ApiResponse.<String>builder().
                       status(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                       .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                       .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handleValidation (MethodArgumentNotValidException mnve){
        String enumKey = Objects.requireNonNull(mnve.getFieldError()).getDefaultMessage();
        try{
            ErrorCode errorCode = ErrorCode.valueOf(enumKey);
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .status(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .build()
            );
        }catch (IllegalArgumentException ie){
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .status(ErrorCode.INVALID_KEY.getCode())
                            .message(ErrorCode.INVALID_KEY.getMessage())
                            .build()
            );
        }

    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<String>> handleAppException(AppException ae){
        ErrorCode errorCode = ae.getErrorCode();
        return ResponseEntity.badRequest()
                .body(ApiResponse.<String>builder()
                        .status(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());    }
}

package com.test.identity_service.exception;

import com.test.identity_service.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

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
            Map<String,Object> attributes = null;
            var constrainValidation = mnve.getBindingResult()
                    .getAllErrors().getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constrainValidation.getConstraintDescriptor().getAttributes();

            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .status(errorCode.getCode())
                            .message(
                                    Objects.nonNull(attributes) ?
                                    mapAttribute(errorCode.getMessage(),attributes):
                                    errorCode.getMessage()

                            )
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
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.<String>builder()
                        .status(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ade){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.<String>builder()
                        .status(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String,Object> mapAttribute){
        String minValue = String.valueOf(mapAttribute.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}",minValue);
    }
}

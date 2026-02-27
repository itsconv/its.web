package com.itsconv.web.common.exception;

import com.itsconv.web.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("Business exception: code={}, message={}", errorCode.name(), e.getMessage(), e);

        ApiResponse<Void> response = ApiResponse.error(
                errorCode.getStatus().value(),
                errorCode.name(),
                e.getMessage() != null ? e.getMessage() : errorCode.getMessage(),
                null
        );
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        String validationMessage = e.getBindingResult().getAllErrors().isEmpty()
                ? ErrorCode.COMMON_BAD_REQUEST.getMessage()
                : e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("Validation exception: {}", validationMessage, e);

        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.COMMON_BAD_REQUEST.name(),
                validationMessage,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unhandled exception", e);

        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorCode.COMMON_INTERNAL_SERVER_ERROR.name(),
                ErrorCode.COMMON_INTERNAL_SERVER_ERROR.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

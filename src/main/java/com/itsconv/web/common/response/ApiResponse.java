package com.itsconv.web.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.common.exception.SuccessCode;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int status,
        String code,
        String message,
        T data
) {

    public static <T> ApiResponse<T> success() {
        return success(SuccessCode.COMMON_SUCCESS, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(SuccessCode.COMMON_SUCCESS, data);
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return success(HttpStatus.OK.value(), successCode.name(), successCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(String code, String message, T data) {
        return success(HttpStatus.OK.value(), code, message, data);
    }

    public static <T> ApiResponse<T> success(int status, String code, String message, T data) {
        return new ApiResponse<>(status, code, message, data);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), code, message, null);
    }

    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), code, message, data);
    }

    public static <T> ApiResponse<T> error(int status, String code, String message, T data) {
        return new ApiResponse<>(status, code, message, data);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return error(errorCode.getStatus().value(), errorCode.name(), errorCode.getMessage(), null);
    }
}

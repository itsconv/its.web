package com.itsconv.web.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    COMMON_BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 값이 유효하지 않습니다."),
    COMMON_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    COMMON_FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    COMMON_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 데이터를 찾을 수 없습니다."),
    COMMON_CONFLICT(HttpStatus.CONFLICT, "이미 처리된 요청입니다."),
    COMMON_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_DELETED(HttpStatus.FORBIDDEN, "삭제된 사용자입니다."),
    USER_DISABLED(HttpStatus.FORBIDDEN, "사용 중지된 사용자입니다."),
    USER_LOCKED(HttpStatus.LOCKED, "잠금된 사용자입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

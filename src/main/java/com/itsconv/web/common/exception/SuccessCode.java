package com.itsconv.web.common.exception;

import lombok.Getter;

@Getter
public enum SuccessCode {
    COMMON_SUCCESS("요청이 성공적으로 처리되었습니다."),
    USER_CREATED("관리자가 등록되었습니다.")
    ;

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }
}

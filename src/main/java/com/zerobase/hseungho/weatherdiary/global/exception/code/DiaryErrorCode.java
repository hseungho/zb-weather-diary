package com.zerobase.hseungho.weatherdiary.global.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DiaryErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부오류가 발생하였습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."),
    INVALID_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 날짜입니다. 너무 과거 혹은 미래의 날짜입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    ;

    private final HttpStatus status;
    private final String message;

    DiaryErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

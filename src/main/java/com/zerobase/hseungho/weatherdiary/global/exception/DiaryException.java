package com.zerobase.hseungho.weatherdiary.global.exception;

import com.zerobase.hseungho.weatherdiary.global.exception.code.DiaryErrorCode;
import lombok.Getter;

@Getter
public class DiaryException extends RuntimeException {

    private final DiaryErrorCode errorCode;
    private final String message;

    public DiaryException(DiaryErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
    public DiaryException(DiaryErrorCode errorCode, String detailMessage) {
        this.errorCode = errorCode;
        this.message = detailMessage;
    }
}

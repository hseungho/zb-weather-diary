package com.zerobase.hseungho.weatherdiary.global.exception;

import com.zerobase.hseungho.weatherdiary.global.exception.code.DiaryErrorCode;

public class InternalServerErrorException extends DiaryException {
    public InternalServerErrorException() {
        super(DiaryErrorCode.INTERNAL_SERVER_ERROR);
    }
    public InternalServerErrorException(String message) {
        super(DiaryErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}

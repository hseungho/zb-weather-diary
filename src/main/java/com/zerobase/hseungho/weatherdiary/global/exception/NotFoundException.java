package com.zerobase.hseungho.weatherdiary.global.exception;

import com.zerobase.hseungho.weatherdiary.global.exception.code.DiaryErrorCode;

public class NotFoundException extends DiaryException {
    public NotFoundException() {
        super(DiaryErrorCode.NOT_FOUND);
    }
    public NotFoundException(String msg) {
        super(DiaryErrorCode.NOT_FOUND, msg);
    }
}

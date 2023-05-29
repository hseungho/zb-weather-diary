package com.zerobase.hseungho.weatherdiary.global.exception;

import com.zerobase.hseungho.weatherdiary.global.exception.code.DiaryErrorCode;

public class InvalidDateException extends DiaryException {
    public InvalidDateException() {
        super(DiaryErrorCode.INVALID_DATE);
    }
}

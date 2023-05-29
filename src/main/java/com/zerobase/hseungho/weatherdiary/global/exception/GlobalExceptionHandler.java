package com.zerobase.hseungho.weatherdiary.global.exception;

import com.zerobase.hseungho.weatherdiary.WeatherDiaryApplication;
import com.zerobase.hseungho.weatherdiary.global.exception.code.DiaryErrorCode;
import com.zerobase.hseungho.weatherdiary.global.exception.object.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(WeatherDiaryApplication.class);

    @ExceptionHandler(DiaryException.class)
    public ErrorResponse handleDiaryException(DiaryException e, HttpServletResponse response) {
        log.error("occurred error by diary exception.", e);
        response.setStatus(e.getErrorCode().getStatus().value());
        return ErrorResponse.of(e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateTimeParseException(MethodArgumentTypeMismatchException e) {
        log.error("occurred error by DateTimeParseException.");
        return ErrorResponse.of(DiaryErrorCode.BAD_REQUEST, "날짜로 요청해야 합니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAllException(Exception e) {
        log.error("occurred error by exception.", e);
        return ErrorResponse.of(DiaryErrorCode.INTERNAL_SERVER_ERROR);
    }

}

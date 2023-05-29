package com.zerobase.hseungho.weatherdiary.global.exception.object;

import com.zerobase.hseungho.weatherdiary.global.exception.DiaryException;
import com.zerobase.hseungho.weatherdiary.global.exception.code.DiaryErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@ApiModel(value = "Error Response", description = "에러 응답 객체입니다.")
public class ErrorResponse {
    @ApiModelProperty(value = "에러코드")
    private final DiaryErrorCode errorCode;
    @ApiModelProperty(value = "에러메시지")
    private final String message;

    public static ErrorResponse of(DiaryException e) {
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }

    public static ErrorResponse of(DiaryErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }

    public static ErrorResponse of(DiaryErrorCode errorCode) {
        return new ErrorResponse(errorCode, errorCode.getMessage());
    }
}

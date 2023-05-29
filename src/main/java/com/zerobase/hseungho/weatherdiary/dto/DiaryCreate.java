package com.zerobase.hseungho.weatherdiary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiaryCreate {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ApiModel(value = "Diary Create Object", description = "다이어리 생성 시 응답되는 객체입니다")
    public static class Response {
        @ApiModelProperty(value = "다이어리 ID")
        private Integer id;
        @ApiModelProperty(value = "다이어리 날씨")
        private String weather;
        @ApiModelProperty(value = "다이어리 기온")
        private Double temperature;
        @ApiModelProperty(value = "다이어리 텍스트")
        private String text;
        @ApiModelProperty(value = "다이어리 일자")
        private LocalDate date;
        @ApiModelProperty(value = "다이어리 생성시간")
        private LocalDateTime createdAt;

        public static Response fromDto(DiaryDto dto) {
            return Response.builder()
                    .id(dto.getId())
                    .weather(dto.getWeather())
                    .temperature(dto.getTemperature())
                    .text(dto.getText())
                    .date(dto.getDate())
                    .createdAt(dto.getCreatedAt())
                    .build();
        }
    }

}

package com.zerobase.hseungho.weatherdiary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DiaryRead {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ApiModel(value = "Diary Read Object", description = "다이어리 조회 시 응답되는 객체입니다.")
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
        @ApiModelProperty(value = "다이어리 수정시간")
        private LocalDateTime updatedAt;

        public static Response fromDto(DiaryDto dto) {
            return Response.builder()
                    .id(dto.getId())
                    .weather(dto.getWeather())
                    .temperature(dto.getTemperature())
                    .text(dto.getText())
                    .date(dto.getDate())
                    .createdAt(dto.getCreatedAt())
                    .updatedAt(dto.getUpdatedAt())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ApiModel(value = "Diary Read Array Object", description = "다이어리 조회 시 응답되는 배열 객체입니다.")
    public static class ListResponse {
        @ApiModelProperty(value = "다이어리 리스트")
        private List<Response> diaries;

        public static ListResponse fromDtoList(List<DiaryDto> dtos) {
            return ListResponse.builder()
                    .diaries(dtos.stream()
                            .map(Response::fromDto)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

}

package com.zerobase.hseungho.weatherdiary.dto;

import com.zerobase.hseungho.weatherdiary.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryDto {

    private Integer id;
    private String weather;
    private Double temperature;
    private String text;
    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DiaryDto fromEntity(Diary entity) {
        return DiaryDto.builder()
                .id(entity.getId())
                .weather(entity.getWeather())
                .temperature(entity.getTemperature())
                .text(entity.getText())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

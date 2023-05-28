package com.zerobase.hseungho.weatherdiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiDto {

    private String weather;
    private String icon;
    private Double temperature;

}

package com.zerobase.hseungho.weatherdiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class OpenWeatherApi {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String weather;
        private String icon;
        private Double temperature;
    }

}

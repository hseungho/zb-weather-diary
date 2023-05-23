package com.zerobase.hseungho.weatherdiary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DiaryService {

    @Value("${openweathermap.key}")
    private String apiKey;

    public void createDiary(LocalDate date, String text) {

    }

    private String getWeatherString() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid="+apiKey;
        return "";
    }

}

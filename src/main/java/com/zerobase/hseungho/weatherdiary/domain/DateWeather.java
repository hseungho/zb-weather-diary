package com.zerobase.hseungho.weatherdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "date_weather")
@NoArgsConstructor
@AllArgsConstructor
public class DateWeather {

    @Id
    private LocalDate date;
    private String weather;
    private String icon;
    private double temperature;

    public static DateWeather of(String weather, String icon, double temperature) {
        return new DateWeather(LocalDate.now(), weather, icon, temperature);
    }
}

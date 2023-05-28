package com.zerobase.hseungho.weatherdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Diary extends BaseEntity {

    private String weather;
    private String icon;
    private double temperature;
    private String text;
    private LocalDate date;

    public static Diary of(String weather, String icon, double temperature,
                           String text, LocalDate date) {
        return new Diary(weather, icon, temperature, text, date);
    }

    public static Diary of(DateWeather weather, String text, LocalDate date) {
        return Diary.of(weather.getWeather(), weather.getIcon(), weather.getTemperature(), text, date);
    }

    public void updateText(String text) {
        this.text = text;
    }
}

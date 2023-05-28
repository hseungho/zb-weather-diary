package com.zerobase.hseungho.weatherdiary.repository;

import com.zerobase.hseungho.weatherdiary.domain.DateWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DateWeatherRepository extends JpaRepository<DateWeather, LocalDate> {
    List<DateWeather> findAllByDate(LocalDate date);
}

package com.zerobase.hseungho.weatherdiary.service;

import com.zerobase.hseungho.weatherdiary.WeatherDiaryApplication;
import com.zerobase.hseungho.weatherdiary.domain.DateWeather;
import com.zerobase.hseungho.weatherdiary.domain.Diary;
import com.zerobase.hseungho.weatherdiary.dto.DiaryDto;
import com.zerobase.hseungho.weatherdiary.dto.OpenWeatherApi;
import com.zerobase.hseungho.weatherdiary.global.exception.InternalServerErrorException;
import com.zerobase.hseungho.weatherdiary.global.exception.InvalidDateException;
import com.zerobase.hseungho.weatherdiary.global.exception.NotFoundException;
import com.zerobase.hseungho.weatherdiary.repository.DateWeatherRepository;
import com.zerobase.hseungho.weatherdiary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private static final Logger log = LoggerFactory.getLogger(WeatherDiaryApplication.class);

    @Value("${openweathermap.key}")
    private String apiKey;
    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;

    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherDate() {
        log.info("success get weather date on scheduled.");
        dateWeatherRepository.save(getWeatherFromApi());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DiaryDto createDiary(LocalDate date, String text) {
        validateDate(date);

        DateWeather dateWeather = getDateWeather(date);
        Diary newDiary = diaryRepository.save(Diary.of(dateWeather, text, date));
        log.info("success create diary. diary_id={}", newDiary.getId());
        return DiaryDto.fromEntity(newDiary);
    }

    @Transactional(readOnly = true)
    public List<DiaryDto> readDiary(LocalDate date) {
        log.debug("request read diary. date={}", date);

        validateDate(date);

        return diaryRepository.findAllByDate(date).stream()
                .map(DiaryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DiaryDto> readDiaries(LocalDate startDate, LocalDate endDate) {
        log.debug("request read diaries between {} - {}.", startDate, endDate);

        validateDates(startDate, endDate);

        return diaryRepository.findAllByDateBetween(startDate, endDate).stream()
                .map(DiaryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DiaryDto updateDiary(LocalDate date, String text) {
        validateDate(date);

        Diary diary = diaryRepository.getFirstByDate(date)
                .orElseThrow(() -> new NotFoundException("다이어리를 찾을 수 없습니다."));
        diary.updateText(text);
        Diary updateDiary = diaryRepository.save(diary);
        log.info("success update diary. diary_id={}.", updateDiary.getId());
        return DiaryDto.fromEntity(updateDiary);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteDiary(LocalDate date) {
        validateDate(date);

        diaryRepository.deleteAllByDate(date);
        log.info("success delete diary. date={}", date);
    }

    private DateWeather getWeatherFromApi() {
        String weatherData = getWeatherString();

        OpenWeatherApi.Response weatherDto = parseWeather(weatherData);

        return DateWeather.of(weatherDto.getWeather(), weatherDto.getIcon(), weatherDto.getTemperature());
    }

    private DateWeather getDateWeather(LocalDate date) {
        List<DateWeather> weathers = dateWeatherRepository.findAllByDate(date);

        return CollectionUtils.isEmpty(weathers) ? getWeatherFromApi() : weathers.get(0);
    }

    private String getWeatherString() {
        try {
            log.info("start get open weather api.");

            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
            BufferedReader br;
            if (code == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                log.error("get error response open weather api. code={}", code);
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            log.info("finish get open weather api.");
            return response.toString();
        } catch (Exception e) {
            log.error("failed to get open weather api response by exception.", e);
            return "failed to get response";
        }
    }

    private OpenWeatherApi.Response parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            log.error("occurred error that json parsing, while open api used. json={}", jsonString);
            throw new InternalServerErrorException("날씨 API 기능을 수행하는데에 오류가 발생하였습니다.");
        }
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        JSONObject mainData = (JSONObject) jsonObject.get("main");

        return OpenWeatherApi.Response.builder()
                .weather(weatherData.get("main").toString())
                .icon(weatherData.get("icon").toString())
                .temperature(Double.parseDouble(mainData.get("temp").toString()))
                .build();
    }

    private void validateDates(LocalDate... dates) {
        for (LocalDate date : dates) {
            validateDate(date);
        }
    }

    private void validateDate(LocalDate date) {
        if (date.isAfter(LocalDate.ofYearDay(3050, 1))) {
            log.error("request invalid date. date={}", date);
            throw new InvalidDateException();
        }
    }

}

package com.zerobase.hseungho.weatherdiary.service;

import com.zerobase.hseungho.weatherdiary.domain.DateWeather;
import com.zerobase.hseungho.weatherdiary.domain.Diary;
import com.zerobase.hseungho.weatherdiary.dto.OpenWeatherApi;
import com.zerobase.hseungho.weatherdiary.global.exception.InternalServerErrorException;
import com.zerobase.hseungho.weatherdiary.global.exception.NotFoundException;
import com.zerobase.hseungho.weatherdiary.repository.DateWeatherRepository;
import com.zerobase.hseungho.weatherdiary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

@Service
@RequiredArgsConstructor
public class DiaryService {

    @Value("${openweathermap.key}")
    private String apiKey;
    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;

    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherDate() {
        dateWeatherRepository.save(getWeatherFromApi());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createDiary(LocalDate date, String text) {
        DateWeather dateWeather = getDateWeather(date);

        diaryRepository.save(Diary.of(dateWeather, text, date));
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date) {
        return diaryRepository.findAllByDate(date);
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateDiary(LocalDate date, String text) {
        Diary diary = diaryRepository.getFirstByDate(date)
                .orElseThrow(() -> new NotFoundException("다이어리를 찾을 수 없습니다."));
        diary.updateText(text);
        diaryRepository.save(diary);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
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
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
            BufferedReader br;
            if (code == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            return "failed to get response";
        }
    }

    private OpenWeatherApi.Response parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
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

}

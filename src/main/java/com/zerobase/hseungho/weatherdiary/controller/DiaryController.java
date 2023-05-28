package com.zerobase.hseungho.weatherdiary.controller;

import com.zerobase.hseungho.weatherdiary.domain.Diary;
import com.zerobase.hseungho.weatherdiary.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @ApiOperation("일기 텍스트와 날씨를 이용해서 DB에 일기 저장")
    @PostMapping("/diary")
    public void createDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "일기를 저장할 날짜", example = "2020-02-02") LocalDate date,
                            @RequestBody
                            @ApiParam(value = "일기에 저장할 텍스트", example = "하루일기") String text) {
        diaryService.createDiary(date, text);
    }

    @ApiOperation("선택한 날짜의 모든 일기 데이터를 조회")
    @GetMapping("/diary")
    public List<Diary> readDiary(@RequestParam
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @ApiParam(value = "조회할 일기의 날짜", example = "2020-02-02") LocalDate date) {
        return diaryService.readDiary(date);
    }

    @ApiOperation("선택한 기간 사이의 모든 일기 데이터를 조회")
    @GetMapping("/diaries")
    public List<Diary> readDiaries(@RequestParam
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @ApiParam(value = "조회할 기간의 시작 날짜", example = "2020-02-02") LocalDate startDate,
                                   @RequestParam
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @ApiParam(value = "조회할 기간의 마지막 날짜", example = "2020-02-20") LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @ApiOperation("선택한 날짜의 첫번째 일기의 텍스트를 수정")
    @PutMapping("/diary")
    public void updateDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "수정할 일기의 날짜", example = "2020-02-02") LocalDate date,
                            @RequestBody
                            @ApiParam(value = "일기에 수정할 텍스트", example = "하루일기") String text) {
        diaryService.updateDiary(date, text);
    }

    @ApiOperation("선택한 날짜의 모든 일기 데이터를 삭제")
    @DeleteMapping("/diary")
    public void deleteDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "삭제할 일기들의 날짜", example = "2020-02-02") LocalDate date) {
        diaryService.deleteDiary(date);
    }
}

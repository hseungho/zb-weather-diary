package com.zerobase.hseungho.weatherdiary.controller;

import com.zerobase.hseungho.weatherdiary.dto.DiaryCreate;
import com.zerobase.hseungho.weatherdiary.dto.DiaryRead;
import com.zerobase.hseungho.weatherdiary.dto.DiaryUpdate;
import com.zerobase.hseungho.weatherdiary.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @ApiOperation(
            value = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장",
            produces = "application/json",
            response = DiaryCreate.Response.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "생성 성공"),
            @ApiResponse(code = 400, message = "요청일자 유효성 실패 및 기타 잘못된 요청"),
            @ApiResponse(code = 500, message = "내부 서버 오류")
    })
    @PostMapping("/diary")
    public DiaryCreate.Response createDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "일기를 저장할 날짜", example = "2020-02-02") LocalDate date,
                                   @RequestBody
                            @ApiParam(value = "일기에 저장할 텍스트", example = "하루일기") String text) {
        return DiaryCreate.Response.fromDto(diaryService.createDiary(date, text));
    }

    @ApiOperation(
            value = "선택한 날짜의 모든 일기 데이터를 조회",
            produces = "application/json",
            response = DiaryRead.ListResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 400, message = "요청일자 유효성 실패 및 기타 잘못된 요청"),
            @ApiResponse(code = 500, message = "내부 서버 오류")
    })
    @GetMapping("/diary")
    public DiaryRead.ListResponse readDiary(@RequestParam
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @ApiParam(value = "조회할 일기의 날짜", example = "2020-02-02") LocalDate date) {
        return DiaryRead.ListResponse.fromDtoList(diaryService.readDiary(date));
    }

    @ApiOperation(
            value = "선택한 기간 사이의 모든 일기 데이터를 조회",
            produces = "application/json",
            response = DiaryRead.ListResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 400, message = "요청일자 유효성 실패 및 기타 잘못된 요청"),
            @ApiResponse(code = 500, message = "내부 서버 오류")
    })
    @GetMapping("/diaries")
    public DiaryRead.ListResponse readDiaries(@RequestParam
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @ApiParam(value = "조회할 기간의 시작 날짜", example = "2020-02-02") LocalDate startDate,
                                   @RequestParam
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @ApiParam(value = "조회할 기간의 마지막 날짜", example = "2020-02-20") LocalDate endDate) {
        return DiaryRead.ListResponse.fromDtoList(diaryService.readDiaries(startDate, endDate));
    }

    @ApiOperation(
            value = "선택한 날짜의 첫번째 일기의 텍스트를 수정",
            produces = "application/json",
            response = DiaryUpdate.Response.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "요청일자 유효성 실패 및 기타 잘못된 요청"),
            @ApiResponse(code = 404, message = "리소스 없읍"),
            @ApiResponse(code = 500, message = "내부 서버 오류")
    })
    @PutMapping("/diary")
    public DiaryUpdate.Response updateDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "수정할 일기의 날짜", example = "2020-02-02") LocalDate date,
                                   @RequestBody
                            @ApiParam(value = "일기에 수정할 텍스트", example = "하루일기") String text) {
        return DiaryUpdate.Response.fromDto(diaryService.updateDiary(date, text));
    }

    @ApiOperation(value = "선택한 날짜의 모든 일기 데이터를 삭제")
    @ApiResponses({
            @ApiResponse(code = 204, message = "삭제 성공"),
            @ApiResponse(code = 400, message = "요청일자 유효성 실패 및 기타 잘못된 요청"),
            @ApiResponse(code = 500, message = "내부 서버 오류")
    })
    @DeleteMapping("/diary")
    public void deleteDiary(@RequestParam
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "삭제할 일기들의 날짜", example = "2020-02-02") LocalDate date) {
        diaryService.deleteDiary(date);
    }
}

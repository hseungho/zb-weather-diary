package com.zerobase.hseungho.weatherdiary.repository;

import com.zerobase.hseungho.weatherdiary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    List<Diary> findAllByDate(LocalDate date);
    List<Diary> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
    Optional<Diary> getFirstByDate(LocalDate date);
    void deleteAllByDate(LocalDate date);

}

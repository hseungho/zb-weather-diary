package com.zerobase.hseungho.weatherdiary.repository;

import com.zerobase.hseungho.weatherdiary.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemoRepository extends JpaRepository<Memo, Integer> {

}

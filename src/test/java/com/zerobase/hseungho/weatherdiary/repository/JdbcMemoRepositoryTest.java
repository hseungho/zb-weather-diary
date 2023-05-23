package com.zerobase.hseungho.weatherdiary.repository;

import com.zerobase.hseungho.weatherdiary.domain.Memo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcMemoRepositoryTest {

    @Autowired
    private JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemoTest() {
        // given
        final int id = 2;
        final String text = "insert memo test";
        Memo newMemo = new Memo(id, text);

        // when
        jdbcMemoRepository.save(newMemo);

        // then
        Optional<Memo> result = jdbcMemoRepository.findById(id);
        assertEquals(text, result.get().getText());
    }

    @Test
    void findAllMemoTest() {
        // given
        // when
        List<Memo> memos = jdbcMemoRepository.findAll();
        // then
        System.out.println(memos);
        assertNotNull(memos);
    }

}
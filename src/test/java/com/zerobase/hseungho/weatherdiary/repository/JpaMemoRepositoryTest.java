package com.zerobase.hseungho.weatherdiary.repository;

import com.zerobase.hseungho.weatherdiary.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class JpaMemoRepositoryTest {

    @Autowired
    private JpaMemoRepository memoRepository;

    @Test
    void insertMemoTest() {
        // given
        final int id = 10;
        final String text = "this is jpa memo";
        Memo memo = new Memo(id, text);
        // when
        memoRepository.save(memo);
        // then
        List<Memo> memos = memoRepository.findAll();
        assertTrue(memos.size() > 0);
    }

    @Test
    void findByIdTest() {
        // given
        final int id = 11;
        final String text =  "jpa";
        Memo newMemo = new Memo(id, text);
        // when
        Memo memo = memoRepository.save(newMemo);
        // then
        Optional<Memo> result = memoRepository.findById(memo.getId());
        assertEquals(text, result.get().getText());
    }

}
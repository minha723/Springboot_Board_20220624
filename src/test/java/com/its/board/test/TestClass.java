package com.its.board.test;

import com.its.board.dto.BoardDTO;
import com.its.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestClass {

    @Autowired
    private BoardService boardService;

    public BoardDTO newBoard(int i){
        BoardDTO member =
                new BoardDTO("테스트제목" + i, "테스트용작성자" + i, "테스트용비밀번호" + i, "테스트용내용" + i, 0);
        return member;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("작성 테스트")
    public void saveTest(){
        Long savedId = boardService.save(newBoard(1));
        assertThat(newBoard(1).getBoardWriter()).isEqualTo(boardService.findById(savedId).getBoardWriter());
    }

    @Test
    @DisplayName("게시물 저장")
    public void boardSave(){
        IntStream.rangeClosed(1,15).forEach(i->{
            boardService.save(newBoard(i));
        });
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("목록테스트")
    public void findAllTest(){
        IntStream.rangeClosed(1,3).forEach(i->{
            boardService.save(newBoard(i));
        });
        assertThat(boardService.findAll().size()).isEqualTo(3);
    }


}

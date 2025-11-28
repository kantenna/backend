package com.example.jpa.repository;

import static org.mockito.ArgumentMatchers.intThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest(){
        // Board 10개 삽입
        for (Long i = 1L; i < 11L; i++) {
            Board board = Board.builder()
            .title("Title"+i)
            .content("content"+i)
            .writer("writer"+i)
            .build();
            boardRepository.save(board);
        }
    }

    @Test
    public void updateTest(){
        // 수정 : title, content
        Board board = boardRepository.findById(5L).get();
        board.changeTitle("TT");
        board.changeContent("TT content");
        boardRepository.save(board);
    }

    @Test
    public void deleteTest(){
        // 삭제
        boardRepository.deleteById(6L);
    }

    // 조회
    @Test
    public void readTest(){
        System.out.println(boardRepository.findById(10L).get());

    }

    @Test
    public void readTest2(){
        boardRepository.findAll().forEach(board -> System.out.println(board));

    }



}

package com.example.jpa.repository;

import static org.mockito.ArgumentMatchers.intThat;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void testFindBy(){
        List<Board> list = boardRepository.findByTitle("Title1");
        System.out.println("findByTitle(\"Title1\")"+list);
        
        list = boardRepository.findByContent("content2");
        System.out.println("findByContent(\"content2\")"+list);
        
        list = boardRepository.findByTitleStartingWith("Title");
        System.out.println("findByTitleStartingWith(\"Title\")"+list);
        
        list = boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc("tle", 5L);
        System.out.println("findByTitleContainingAndIdGreaterThan(\"tle\", 5L)"+list);
        
        list = boardRepository.findByWriterContaining("6");
        System.out.println("findByWriterContaining(\"6\")"+list);
        
        list = boardRepository.findByTitleContainingOrContentContaining("9", "8");
        System.out.println("findByTitleContaingAndContentContaing(\"9\", \"8\")"+list);
    }

    @Test
    public void testFindBy2(){
        List<Board> list = boardRepository.findByTitle2("Title1");
        System.out.println("findByTitle(\"Title1\")"+list);
        
        list = boardRepository.findByContent2("content2");
        System.out.println("findByContent(\"content2\")"+list);
        
        list = boardRepository.findByTitleStartingWith2("Title");
        System.out.println("findByTitleStartingWith(\"Title\")"+list);
        
        list = boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc2("tle", 5L);
        System.out.println("findByTitleContainingAndIdGreaterThan(\"tle\", 5L)"+list);
        
        list = boardRepository.findByWriterContaining2("6");
        System.out.println("findByWriterContaining(\"6\")"+list);
        
        list = boardRepository.findByTitleContainingOrContentContaining2("9", "8");
        System.out.println("findByTitleContaingAndContentContaing(\"9\", \"8\")"+list);
    }

    @Test
    public void testFindBy3(){
        List<Board> list = boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc3("tle", 5L);
        System.out.println("findByTitleContainingAndIdGreaterThan(\"tle\", 5L)"+list);
    }

    @Test
    public void testFindBy4(){
        List<Object[]> list = boardRepository.findByTitle3("Title");
        for (Object[] objects : list) {
            // System.out.println(Arrays.toString(objects));
            String title = (String)objects[0];
            String writer = (String)objects[1];
            System.out.println(title +" "+ writer);
        }
    }
}

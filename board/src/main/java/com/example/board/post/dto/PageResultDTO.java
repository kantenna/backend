package com.example.board.post.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;

@Data
public class PageResultDTO<E> {
    
    // 화면에 보여줄 목록
    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int nextPage, prevPage, totalPage, current;

    private long totalCount;

    @Builder(builderMethodName = "withAll")
    public PageResultDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = totalCount;

        // 10.0 으로 안하면 소숫점 아래가 안나와서 안되더라
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) *10;
        int start = end - 9;

        int last = (int)(Math.ceil(totalCount/(double)pageRequestDTO.getSize()));

        end = end > last ? last : end;
        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();
        
        if (prev) {
            this.prevPage = start -1;
        }
        if (next) {
            this.nextPage = end + 1;
        }
        
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();
    }
    
}

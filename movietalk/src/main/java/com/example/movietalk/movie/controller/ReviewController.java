package com.example.movietalk.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;




@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
@RestController
public class ReviewController {
    // 특정 영화에 달려있는 모든 리뷰 가져오기 /reviews/300(mno)/all + GET
    @GetMapping("/{mno}/all")
    public String getReviews(@PathVariable Long mno) {
        return new String();
    }
    
    
    // 특정 영화의 리뷰 수정
    // 1) 리뷰 가져오기 /reviews/mno/rno + GET
    @GetMapping("/{mno}/{rno}")
    public String getReview(@PathVariable Long rno) {
        return new String();
    }
    
    // 2) 수정 /reviews/300/rno + PUT
    @PutMapping("/{mno}/{rno}")
    public String putReview(@PathVariable Long rno, @RequestBody String entity) {
        
        return entity;
    }

    // 특정 영화의 리뷰 삭제 /reviews/300/rno + DELETE
    @DeleteMapping("/{mno}/{rno}")
    public String deleteReview(@PathVariable Long rno, @RequestBody String entity) {
        
        return entity;
    }

    // 특정 영화의 리뷰 추가 /reviews/300 + POST
    @PostMapping("/{mno}")
    public String postReview(@PathVariable Long mno) {
        
        return "";
    }
    
}

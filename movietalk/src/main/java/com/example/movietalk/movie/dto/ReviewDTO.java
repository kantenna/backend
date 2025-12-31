package com.example.movietalk.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ReviewDTO {
    
    private Long rno;

    private int grade; // 리뷰 점수

    private String text; // 리뷰

    // movie 정보는 mno만
    private Long mno;

    // member 정보는 mid만
    private Long mid;
    private String email;
    private String nickname;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;


}

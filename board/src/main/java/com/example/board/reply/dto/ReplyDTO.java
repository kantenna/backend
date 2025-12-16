package com.example.board.reply.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReplyDTO {
    private Long rno;

    private String text;

    private String replyer;

    private Long bno;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    
}

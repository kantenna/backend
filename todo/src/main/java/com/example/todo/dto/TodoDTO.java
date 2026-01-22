package com.example.todo.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO {

    private Long id;

    private String title;

    private boolean completed;

    private boolean important;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}

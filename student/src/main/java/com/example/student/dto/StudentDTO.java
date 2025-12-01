package com.example.student.dto;

import java.time.LocalDateTime;

import com.example.student.entity.constant.Grade;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String addr;
    private String gender;
    private Grade grade;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
}

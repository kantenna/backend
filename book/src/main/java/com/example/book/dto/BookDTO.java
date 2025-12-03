package com.example.book.dto;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private int price;
    private String author;
}

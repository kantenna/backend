package com.example.book.dto;

import groovy.transform.builder.Builder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "isbn 필수 요소입니다.")
    private String isbn;

    @NotBlank(message = "title 필수 요소입니다.")
    private String title;

    @Max(value = 10000000, message = "가격은 최대 1000만원 이하여야 합니다.")
    @Min(value = 0, message = "가격은 최소 0 이상이여야 합니다.")
    @NotNull(message = "price 필수 요소입니다.")
    private Integer price;

    @NotBlank(message = "author 필수 요소입니다.")
    private String author;

    private String description;
}

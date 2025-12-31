package com.example.movietalk.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movietalk.member.entity.Member;
import com.example.movietalk.movie.dto.ReviewDTO;
import com.example.movietalk.movie.entity.Movie;
import com.example.movietalk.movie.entity.Review;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService {
    
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public Long insertRow(ReviewDTO reviewDTO){
        // dto => entity
        Review review = dtoToEntity(reviewDTO);

        return reviewRepository.save(review).getRno();
    }

    public void deleteRow(Long rno){
        reviewRepository.deleteById(rno);
    }

    public Long updateRow(ReviewDTO reviewDTO){
        // 업데이트 할 대상 찾기
        Review review = reviewRepository.findById(reviewDTO.getRno()).get();
        // 업데이트 사항 적용
        review.changeText(reviewDTO.getText());
        review.changeGrade(reviewDTO.getGrade());
        // save() X => dirty checking 덕분에!!
        return review.getRno();
    }

    @Transactional(readOnly = true)
    public ReviewDTO getRow(Long rno){
        return entityToDto(reviewRepository.findById(rno).get());
    }
    
    @Transactional(readOnly = true)
    public List<ReviewDTO> getList(Long mno){

        Movie movie = movieRepository.findById(mno).get();
        List<Review> reviews = reviewRepository.findByMovie(movie);

        // entity => dto, Review => ReviewDTO
        // List<ReviewDTO> list = new ArrayList<>();
        // for (Review review : reviews) {
        //     list.add(entityToDto(review));
        // }

        List<ReviewDTO> list = reviews.stream().map(review -> entityToDto(review)).collect(Collectors.toList());

        return list;
    }

    private Review dtoToEntity(ReviewDTO dto){

        // ReviewDTO => Review 
        Review review = Review.builder()
        .rno(dto.getRno())
        .grade(dto.getGrade())
        .text(dto.getText())
        .movie(Movie.builder().mno(dto.getMno()).build())
        .member(Member.builder().mid(dto.getMid()).build())
        .build();
        
        return review;
    }

    private ReviewDTO entityToDto(Review review){

        // Review => ReviewDTO
        ReviewDTO dto = ReviewDTO.builder()
        .rno(review.getRno())
        .grade(review.getGrade())
        .text(review.getText())
        .mno(review.getMovie().getMno())
        .mid(review.getMember().getMid())
        .email(review.getMember().getEmail())
        .nickname(review.getMember().getNickname())
        .createDate(review.getCreateDate())
        .updateDate(review.getUpdateDate())
        .build();
        
        return dto;
    }

}

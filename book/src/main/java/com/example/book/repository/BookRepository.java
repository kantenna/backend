package com.example.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book>{

    Optional<Book> findByIsbn(String isbn); // == where isbn = 'A1000'

    List<Book> findByTitleContaining(String title); // == where title like '%자바%'

    // where author = ''
    List<Book> findByAuthor(String author);

    // where author like '%'
    List<Book> findByAuthorEndingWith(String author);
    List<Book> findByAuthorStartingWith(String author);
    List<Book> findByAuthorContaining(String author);

    // 도서가격이 12000이상 35000이하
    List<Book> findByPriceBetween(int startPrice, int endPrice);

    public default Predicate makePredicate(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QBook book = QBook.book;

        builder.and(book.id.gt(0));
        return builder;
    }
}

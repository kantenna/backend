package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BookService {
    
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    // crud 메서드 호출하는 서비스 메서드 작성

    public String create(BookDTO dto){
        // bookDTO => entity 변경
        Book book = modelMapper.map(dto, Book.class);
        return bookRepository.save(book).getTitle();
    }

    // R(하나만 조회, 여러 개 조회)
    // 검색 : title => %자바% or isbn, id => 하나만 조회
    @Transactional(readOnly = true)
    public List<BookDTO> readTitle(String title){
        List<Book> result = bookRepository.findByTitleContaining(title);
        
        // List<Book> => List<BookDTO> 변경 후 리턴
        // List<BookDTO> list = new ArrayList<>();
        // result.forEach(book -> {
        //     list.add(modelMapper.map(book, BookDTO.class));
        // });

        List<BookDTO> list = result.stream()
        .map(book -> modelMapper.map(book, BookDTO.class))
        .collect(Collectors.toList());

        return list;
    }

    @Transactional(readOnly = true)
    public BookDTO readIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn).orElseThrow();
        
        // Optional<Book> => BookDTO 변경 후 리턴
        return modelMapper.map(book, BookDTO.class);
    }

    @Transactional(readOnly = true)
    public BookDTO readId(Long id){
        Book book = bookRepository.findById(id).orElseThrow();
        
        // Optional<Book> => BookDTO 변경 후 리턴
        return modelMapper.map(book, BookDTO.class);
    }

    public Long update(BookDTO upDto){
        Book book = bookRepository.findById(upDto.getId()).orElseThrow();
        book.changePrice(upDto.getPrice());
        book.changeDescription(upDto.getDescription());
        // return bookRepository.save(book).getId();
        return book.getId();
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getList(){
        return bookRepository.findAll()
        .stream()
        .map(book -> modelMapper.map(book, BookDTO.class))
        .collect(Collectors.toList());
    }
}

package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RequestMapping("/book")
@Log4j2
@Controller
public class BookController {

    private final BookService bookService;
    
    @GetMapping("/register")
    public void getRegister(BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("등록 화면 요청");
    }

    @PostMapping("/register")
    public String postMethodName(@Valid BookDTO dto, BindingResult result, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("도서등록 요청 {}", dto);

        if (result.hasErrors()) {
            return "/book/register";
        }
        String title = bookService.create(dto);

        rttr.addFlashAttribute("msg", title + " 도서가 등록되었습니다.");
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        return "redirect:/book/list";
    }
    
    @GetMapping("/list")
    public void getList(Model model, PageRequestDTO pageRequestDTO) {
        log.info("목록 화면 요청");
        PageResultDTO<BookDTO> result = bookService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping({"/read", "/modify"})
    public void getRead(@RequestParam Long id, PageRequestDTO pageRequestDTO, Model model) {
        log.info("도서 상세 조회 {} []", id, pageRequestDTO);

        BookDTO dto = bookService.readId(id);
        model.addAttribute("dto", dto);
    }
    
    @PostMapping("/modify")
    public String postModify(@Valid BookDTO dto, PageRequestDTO pageRequestDTO, BindingResult result, RedirectAttributes rttr) {
        log.info("도서상세 수정 요청 {} {}", dto, pageRequestDTO);
        
        if (result.hasErrors()) {
            return "redirect:/book/list";    
        }

        Long id = bookService.update(dto);
        
        // 조회
        rttr.addFlashAttribute("msg"," 도서가 수정되었습니다.");
        rttr.addAttribute("id", id);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        return "redirect:/book/read";
    }
    
    @PostMapping("/remove")
    public String postRemove(@RequestParam Long id, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("도서 삭제 {}", id);

        bookService.delete(id);
        rttr.addFlashAttribute("msg"," 도서가 삭제되었습니다.");
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        return "redirect:/book/list";
    }
    
    
}

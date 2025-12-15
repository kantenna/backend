package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RequestMapping("/memo")
@Log4j2
@Controller // template 필요
public class MemoController {

    private final MemoService memoService;

    @ResponseBody // 리턴값은 데이터임
    @GetMapping("/hello")
    public String getHello() {
        return "Hello World"; // 문자열은 브라우저 해석 가능
    }


    // /memo/sample1/id
    @ResponseBody
    @GetMapping("/sample1/{id}")
    public MemoDTO getMethodName(@PathVariable Long id) {
        
        MemoDTO dto = memoService.read(id);
        return dto;
    }
    
    // ResponseEntity : 데이터 + 상태 코드(200, 400, 500)
    @GetMapping("/sample1/list")
    public ResponseEntity<List<MemoDTO>> getRead2() {
        
        List<MemoDTO> list = memoService.readAll();
        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/list")
    public void getList(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll();
        model.addAttribute("list", list);
    }
    
    @GetMapping({"/read", "modify"})
    public void getRead(@RequestParam Long id, Model model) {
        log.info("memo id {}", id);
        
        MemoDTO dto = memoService.read(id);
        model.addAttribute("dto", dto);
        
    }
    
    @PostMapping("/modify")
    public String postModify(MemoDTO dto, RedirectAttributes rttr) {
        log.info("memo 수정 {}", dto);

        Long id = memoService.modify(dto);
        
        // /memo/read?id=1 이동
        rttr.addAttribute("id", id);
        return "redirect:/memo/read";
    }
    

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long id, RedirectAttributes rttr) {
        log.info("memo remove id {}", id);

        memoService.remove(id);

        // 삭제 후 목록 보여주기
        rttr.addFlashAttribute("msg", "메모가 삭제되었습니다!");
        return "redirect:/memo/list";
    }
    
    @GetMapping("/create")
    public void getcreate(@ModelAttribute("dto") MemoDTO dto) {
        log.info(("추가 페이지 요청"));
    }
    
    @PostMapping("/create")
    public String postcreate( @ModelAttribute("dto") @Valid MemoDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("추가요청 {}", dto);

        // 유효성 검증 조건에 일치하지 않는 경우
        if (result.hasErrors()) {
            return"/memo/create";
        }

        // 일치하는 경우
        Long id = memoService.insert(dto);

        rttr.addFlashAttribute("msg", id + " 번 메모가 삽입되었습니다!");
        return "redirect:/memo/list";
    }
    
}

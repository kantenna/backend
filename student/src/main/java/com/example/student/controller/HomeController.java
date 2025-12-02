package com.example.student.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.student.dto.StudentDTO;




@Log4j2
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String getHome(@ModelAttribute(value = "loginDto") StudentDTO loginDto) {
        return "main";
    }
    
}

package com.example.memo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.java.Log;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Log
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String getHome() {
        return "main";
    }
    
}

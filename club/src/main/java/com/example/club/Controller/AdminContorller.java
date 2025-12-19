package com.example.club.Controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Log4j2
@Controller
public class AdminContorller {
    
    @GetMapping("/admin/manage")
    public void getAdmin() {
        
    }
    

}

package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void testEncoder(){
        String password = "1111";

        // 입력 비밀번호 => 암호화
        String encodePass = passwordEncoder.encode(password);
        // {bcrypt}$2a$10$mZ0.wOkA.CqpAkLKLYwpLu1DoTkyie9u5TTi69g2gMcYpfNNt/tQy
        // $2a$10$Jr2wshZycxYCJleBQ8AiZ.4/9Bbwq6arVPe1XbXVYVdm.foM1BrJi
        System.out.println("raw password "+ password +" encode password " + encodePass);

        System.out.println(passwordEncoder.matches(password, encodePass));
        System.out.println(passwordEncoder.matches("2222", encodePass));
    }

}

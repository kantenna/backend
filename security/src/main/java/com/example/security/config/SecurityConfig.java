package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.log4j.Log4j2;

@EnableWebSecurity // 모든 웹 요청에 대히 Security Filter Chain을 적용
@Log4j2
@Configuration // 스프링 설정 클래스
public class SecurityConfig {
    // 시큐리티 설정 클래스
    
    @Bean // == 객체 생성
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/","sample/guest").permitAll()
            .requestMatchers("/sample/member").hasRole("MEMBER")
            .requestMatchers("/sample/admin").hasRole("ADMIN"))
        // .httpBasic(Customizer.withDefaults());
            .formLogin(login -> login.loginPage("/sample/login").permitAll())
            .logout(logout -> logout
                .logoutUrl("/sample/logout") // 로그아웃 post
                .logoutSuccessUrl("/") // 로그아웃 성공시
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 운영, 실무, 여러 암호화 알고리즘
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // 연습, 단일 알고리즘 사용
        // return new BCryptPasswordEncoder();
    }

    // 임시 User 생성
    @Bean
    UserDetailsService users(){

        UserDetails user = User.builder()
        .username("user1")
        .password("{bcrypt}$2a$10$mZ0.wOkA.CqpAkLKLYwpLu1DoTkyie9u5TTi69g2gMcYpfNNt/tQy")
        .roles("MEMBER")
        .build();

        UserDetails admin = User.builder()
        .username("admin")
        .password("{bcrypt}$2a$10$mZ0.wOkA.CqpAkLKLYwpLu1DoTkyie9u5TTi69g2gMcYpfNNt/tQy")
        .roles("MEMBER","ADMIN")
        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}

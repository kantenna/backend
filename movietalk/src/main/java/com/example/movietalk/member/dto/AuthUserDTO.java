package com.example.movietalk.member.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class AuthUserDTO extends User{

    private CustomUserDTO customUserDTO;
    // Collection : List, Set
    // List list = new ArrayList<>();
    // list.add(new CustomUserDTO());

    public AuthUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    public AuthUserDTO(CustomUserDTO customUserDTO){
        super(customUserDTO.getEmail(), customUserDTO.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + customUserDTO.getRole())));
        this.customUserDTO = customUserDTO;
    }
}

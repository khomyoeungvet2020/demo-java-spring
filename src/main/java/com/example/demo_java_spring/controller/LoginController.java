package com.example.demo_java_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_java_spring.config.JwtService;
import com.example.demo_java_spring.dto.JwtResponseDto;
import com.example.demo_java_spring.dto.LoginRequest;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("login")
    public JwtResponseDto authenticateAndGetToken(@RequestBody LoginRequest authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponseDto.builder()
                    .status(200)
                    .message("Login Successfully")
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String test() {
        try {
            return "Welcome Admin";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String testUser() {
        try {
            return "Welcome User";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

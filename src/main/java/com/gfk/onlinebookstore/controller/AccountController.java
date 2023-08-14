package com.gfk.onlinebookstore.controller;

import com.gfk.onlinebookstore.dto.JWTAuthResponse;
import com.gfk.onlinebookstore.dto.LoginRequest;
import com.gfk.onlinebookstore.dto.UserRequest;
import com.gfk.onlinebookstore.dto.UserResponse;
import com.gfk.onlinebookstore.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        String token = accountService.login(loginRequest);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(accountService.register(userRequest), HttpStatus.CREATED);
    }
}
package com.topicossoftware.marino.upb.edu.api.api.controller;

import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.AuthService;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }
}
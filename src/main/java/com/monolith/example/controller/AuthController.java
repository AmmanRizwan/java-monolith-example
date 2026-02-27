package com.monolith.example.controller;

import com.monolith.example.dto.LoginDto;
import com.monolith.example.dto.SignUpDto;
import com.monolith.example.response.ApiResponse;
import com.monolith.example.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<?>> signUp(
            @RequestBody SignUpDto dto
            ) {
        var data = authService.signUp(dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("SignUp Successfully", data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> logIn(
            @RequestBody LoginDto dto
            ) {
        var data = authService.logIn(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Login Successfully", data));
    }
}

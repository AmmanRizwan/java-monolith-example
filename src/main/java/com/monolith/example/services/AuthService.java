package com.monolith.example.services;

import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.dto.LoginDto;
import com.monolith.example.dto.SignUpDto;

public interface AuthService {
    CustomerResponseDto logIn(LoginDto dto);
    CustomerResponseDto signUp(SignUpDto dto);
}

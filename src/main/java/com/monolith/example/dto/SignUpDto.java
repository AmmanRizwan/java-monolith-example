package com.monolith.example.dto;

public record SignUpDto(
        String name,
        String email,
        String phone,
        String password
) {
}

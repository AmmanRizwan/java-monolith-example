package com.monolith.example.dto;

public record CustomerResponseDto(
        Long id,
        String name,
        String email,
        String phone
) {
}

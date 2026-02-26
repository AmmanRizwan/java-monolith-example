package com.monolith.example.dto;

public record CustomerDto(
    String name,
    String email,
    String phone,
    String password
) {
}

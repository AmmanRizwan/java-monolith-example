package com.monolith.example.dto;

import java.util.Date;

public record CustomerResponseDto(
        Long id,
        String name,
        String email,
        String phone,
        Date createdAt,
        Date updatedAt
) {
}

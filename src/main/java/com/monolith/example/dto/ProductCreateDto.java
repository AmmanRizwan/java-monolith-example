package com.monolith.example.dto;

import java.math.BigDecimal;

public record ProductCreateDto(
        String name,
        BigDecimal price,
        String description
) {
}

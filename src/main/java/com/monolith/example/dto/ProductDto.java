package com.monolith.example.dto;

import java.math.BigDecimal;

public record ProductDto(
        String name,
        BigDecimal price,
        String description
) {
}

package com.monolith.example.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
    Long id,
    String name,
    BigDecimal price,
    String description
) {
}

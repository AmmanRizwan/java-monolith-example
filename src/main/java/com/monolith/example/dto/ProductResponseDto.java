package com.monolith.example.dto;

import java.math.BigDecimal;
import java.util.Date;

public record ProductResponseDto(
    Long id,
    String name,
    BigDecimal price,
    String description,
    Date createdAt,
    Date updatedAt
) {
}

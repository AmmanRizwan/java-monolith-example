package com.monolith.example.mapper;

import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public Product toProduct(ProductDto dto) {
        var product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());

        return product;
    }
}

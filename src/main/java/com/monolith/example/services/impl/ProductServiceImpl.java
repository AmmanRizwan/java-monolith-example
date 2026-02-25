package com.monolith.example.services.impl;

import com.monolith.example.dto.ProductCreateDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductResponseDto createProduct(ProductCreateDto dto) {
        return null;
    }

    @Override
    public List<ProductResponseDto> findProducts() {
        return List.of();
    }

    @Override
    public ProductResponseDto findProductByName(String name) {
        return null;
    }

    @Override
    public ProductResponseDto findProduct(Long id) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(ProductCreateDto dto, Long id) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}

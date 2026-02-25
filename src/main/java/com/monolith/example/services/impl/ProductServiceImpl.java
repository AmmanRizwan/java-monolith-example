package com.monolith.example.services.impl;

import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.mapper.ProductMapper;
import com.monolith.example.repository.ProductRepository;
import com.monolith.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductDto dto) {
        var product = productMapper.toProduct(dto);
        var saveProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(saveProduct);
    }

    @Override
    public List<ProductResponseDto> findProducts() {
        return productRepository
                    .findAll()
                    .stream()
                    .map(productMapper::toProductResponseDto)
                    .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto findProductByName(String name) {
        var product = productRepository
                    .findByName(name)
                    .orElseThrow(
                            () -> new IllegalArgumentException("product is not found by name: " + name)
                    );

        return productMapper.toProductResponseDto(product);
    }

    @Override
    public ProductResponseDto findProduct(Long id) {
        var product = productRepository
                                .findById(id)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("product is not found by id: " + id)
                                );

        return productMapper.toProductResponseDto(product);
    }

    @Override
    public ProductResponseDto updateProduct(ProductDto dto, Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("cannot find product by id" + id));

        product.setName(dto.name() == null ? product.getName() : dto.name());
        product.setPrice(dto.price() == null ? product.getPrice() : dto.price());
        product.setDescription(dto.description() == null ? product.getDescription() : dto.description());

        var updateProduct = productRepository.save(product);

        return productMapper.toProductResponseDto(updateProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteProductByName(String name) {
        productRepository.deleteByName(name);
    }
}

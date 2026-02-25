package com.monolith.example.controller;

import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.response.ApiResponse;
import com.monolith.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @RequestBody ProductDto dto
            ) {
        var product = productService.createProduct(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product created Successfully", product));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProducts() {
        var products = productService.findProducts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Products fetched Successfully", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(
            @PathVariable("id") Long id
    ) {
        var product = productService.findProduct(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Product fetched Successfully", product));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductByName(
            @PathVariable("name") String name
    ) {
        var product = productService.findProductByName(name);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Product fetched Successfully", product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @RequestBody ProductDto dto,
            @PathVariable("id") Long id
    ) {
        productService.updateProduct(dto, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Product updated Successfully", null));
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProductByName(
            @RequestBody ProductDto dto,
            @PathVariable("name") String name
    ) {
        productService.updateProductByName(dto, name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Product updated Successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> deleteProduct(
            @PathVariable("id") Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Product deleted Successfully", null));
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> deleteProductByName(
            @PathVariable("name") String name
    ) {
        productService.deleteProductByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Product deleted Successfully", null));
    }
}

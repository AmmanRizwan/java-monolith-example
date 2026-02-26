package com.monolith.example.runner;

import com.monolith.example.dto.ProductDto;
import com.monolith.example.mapper.ProductMapper;
import com.monolith.example.model.Product;
import com.monolith.example.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRunnerComponent {

    private final static Logger logger = LoggerFactory.getLogger(ProductRunnerComponent.class);

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductMapper mapper;

    @Bean
    @Profile("test")
    public CommandLineRunner commandLineRunner() {
        return args -> {
            ProductDto dto1 = new ProductDto("Laptop", new BigDecimal("3.3"), "This is a simple laptop");
            ProductDto dto2 = new ProductDto("Phone", new BigDecimal("10.2"), "This is a simple phone");
            ProductDto dto3 = new ProductDto("Camera", new BigDecimal("2.2"), "This is a simple camera");
            ProductDto dto4 = new ProductDto("Tablet", new BigDecimal("10.2"), "This is a simple tablet");
            ProductDto dto5 = new ProductDto("Cable", new BigDecimal("23.3"), "This is a simple cable");

            try {
                List<ProductDto> dtos = new ArrayList<>(List.of(dto1, dto2, dto3, dto4, dto5));
                List<Product> products = dtos.stream().map(mapper::toProduct).toList();

                repository.saveAll(products);
                logger.info("Products are saved in the in-memory database");
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        };
    }
}

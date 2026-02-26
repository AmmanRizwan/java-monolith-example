package com.monolith.example.runner;

import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.ProductDto;
import com.monolith.example.mapper.CustomerMapper;
import com.monolith.example.mapper.ProductMapper;
import com.monolith.example.model.Customer;
import com.monolith.example.model.Product;
import com.monolith.example.repository.CustomerRepository;
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
public class RunnerComponent {

    private final static Logger logger = LoggerFactory.getLogger(RunnerComponent.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    @Profile("test")
    public CommandLineRunner productCommandLineRunner() {
        return args -> {
            ProductDto dto1 = new ProductDto("Laptop", new BigDecimal("3.3"), "This is a simple laptop");
            ProductDto dto2 = new ProductDto("Phone", new BigDecimal("10.2"), "This is a simple phone");
            ProductDto dto3 = new ProductDto("Camera", new BigDecimal("2.2"), "This is a simple camera");
            ProductDto dto4 = new ProductDto("Tablet", new BigDecimal("10.2"), "This is a simple tablet");
            ProductDto dto5 = new ProductDto("Cable", new BigDecimal("23.3"), "This is a simple cable");

            try {
                List<ProductDto> dtos = new ArrayList<>(List.of(dto1, dto2, dto3, dto4, dto5));
                List<Product> products = dtos.stream().map(productMapper::toProduct).toList();

                productRepository.saveAll(products);
                logger.info("Products are saved in the in-memory database");
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        };
    }

    @Bean
    @Profile("test")
    public CommandLineRunner customerCommandLineRunner() {
        return args -> {
            CustomerDto dto1 = new CustomerDto("Amman", "amman@example.com", "123456789", "amman123@");
            CustomerDto dto2 = new CustomerDto("Rizwan", "rizwan@example.com", "147258369", "rizwan123@");
            CustomerDto dto3 = new CustomerDto("Asif", "asif@example.com", "789456123", "asif123@");
            CustomerDto dto4 = new CustomerDto("File", "file@example.com", "456789123", "file123@");

            try {
                List<CustomerDto> dtos = new ArrayList<>(List.of(dto1, dto2, dto3, dto4));
                List<Customer> customers = dtos.stream().map(customerMapper::toCustomer).toList();

                customerRepository.saveAll(customers);
                logger.info("Customers are saved in the in-memory database");

            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        };
    }
}

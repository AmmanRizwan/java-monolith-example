package com.monolith.example;

import com.monolith.example.mapper.ProductMapper;
import com.monolith.example.repository.ProductRepository;
import com.monolith.example.services.impl.ProductServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductMapper mapper;
    @Mock
    private ProductRepository repository;

}

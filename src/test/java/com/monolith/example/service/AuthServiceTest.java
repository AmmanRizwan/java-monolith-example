package com.monolith.example.service;

import com.monolith.example.mapper.CustomerMapper;
import com.monolith.example.repository.CustomerRepository;
import com.monolith.example.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl service;

    @Mock
    private CustomerRepository repository;
    @Mock
    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnCredentialLoginTest() {

    }

    @Test
    public void shouldThrowErrorCustomerNotFoundLoginTest() {

    }

    @Test
    public void shouldThrowErrorCustomerIncorrectPasswordLoginTest() {

    }

    @Test
    public void shouldReturnCredentialSignUpTest() {

    }

    @Test
    public void shouldThrowErrorCustomerExistSignUpTest() {

    }
}

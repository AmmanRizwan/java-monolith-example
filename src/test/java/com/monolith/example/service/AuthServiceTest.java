package com.monolith.example.service;

import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.dto.LoginDto;
import com.monolith.example.mapper.CustomerMapper;
import com.monolith.example.model.Customer;
import com.monolith.example.repository.CustomerRepository;
import com.monolith.example.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
        String customerEmail = "amman@example.com";

        LoginDto dto = new LoginDto("amman@example.com",
                "amman123@");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "Amman",
                "amman@example.com",
                "123456789");

        Mockito.when(repository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        Mockito.when(mapper.toCustomerResponseDto(customer)).thenReturn(response);

        CustomerResponseDto expected = service.logIn(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.email(), "amman@example.com");
        Assertions.assertEquals(expected.id(), 1L);

        Mockito.verify(repository, Mockito.times(1)).findByEmail(customerEmail);
        Mockito.verify(mapper, Mockito.times(1)).toCustomerResponseDto(customer);
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

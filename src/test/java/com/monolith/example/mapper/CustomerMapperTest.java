package com.monolith.example.mapper;

import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.dto.SignUpDto;
import com.monolith.example.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.Instant;

@SpringBootTest
public class CustomerMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerMapperTest.class);

    @Autowired
    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomerMapper();
    }

    @Test
    public void shouldReturnCustomerDtoToCustomer() {
        CustomerDto dto = new CustomerDto(
                "Amman",
                "amman@example.com",
                "123456789",
                "amman123@");

        Customer expected = mapper.toCustomer(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getName(), dto.name());
        Assertions.assertEquals(expected.getEmail(), dto.email());
        Assertions.assertEquals(expected.getPhone(), dto.phone());
        Assertions.assertEquals(expected.getPassword(), dto.password());

        logger.info("Unit Test: CustomerDto->Customer: name={}", dto.name());
    }

    @Test
    public void shouldReturnNullCustomerDtoToCustomer() {
        CustomerDto dto = new CustomerDto(
                null,
                null,
                null,
                null);

        Customer expected = mapper.toCustomer(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertNull(expected.getName());
        Assertions.assertNull(expected.getEmail());
        Assertions.assertNull(expected.getPhone());
        Assertions.assertNull(expected.getPassword());

        logger.warn("Unit Test: CustomerDto->Null");
    }
    
    @Test
    public void shouldReturnSignUpDtoToCustomer() {
        SignUpDto dto = new SignUpDto(
                "Amman", 
                "amman@example.com", 
                "1234156789",
                "amman123@");
        
        Customer expected = mapper.signUpToCustomer(dto);
        
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getName(), dto.name());
        Assertions.assertEquals(expected.getEmail(), dto.email());
        Assertions.assertEquals(expected.getPhone(), dto.phone());
        Assertions.assertEquals(expected.getPassword(), dto.password());

        logger.info("Unit Test: SignUpDto->Customer: name={}", dto.name());
    }

    @Test
    public void shouldReturnNullSignUpDtoToCustomer() {
        SignUpDto dto = new SignUpDto(
                null,
                null,
                null,
                null);

        Customer expected = mapper.signUpToCustomer(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertNull(expected.getName());
        Assertions.assertNull(expected.getEmail());
        Assertions.assertNull(expected.getPhone());
        Assertions.assertNull(expected.getPassword());

        logger.warn("Unit Test: SignUpDto->Null");
    }

    @Test
    public void shouldReturnNullCustomerToCustomerResponseDto() {
        Customer customer = new Customer();

        CustomerResponseDto expected = mapper.toCustomerResponseDto(customer);

        Assertions.assertNotNull(expected);
        Assertions.assertNull(expected.id());
        Assertions.assertNull(expected.name());
        Assertions.assertNull(expected.email());
        Assertions.assertNull(expected.phone());

        logger.warn("Unit Test: Customer->Null");
    }

    @Test
    public void shouldReturnCustomerToCustomerResponseDto() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("*****");
        customer.setCreatedAt(Date.from(Instant.now()));
        customer.setUpdatedAt(Date.from(Instant.now()));

        CustomerResponseDto expected = mapper.toCustomerResponseDto(customer);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.id(), customer.getId());
        Assertions.assertEquals(expected.name(), customer.getName());
        Assertions.assertEquals(expected.email(), customer.getEmail());
        Assertions.assertEquals(expected.phone(), customer.getPhone());
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        logger.info("Unit Test: Customer->CustomerResponseDto: id={}", customer.getId());
    }
}

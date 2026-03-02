package com.monolith.example.service;

import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.dto.LoginDto;
import com.monolith.example.dto.SignUpDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class AuthServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceTest.class);

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
                "123456789",
                Date.from(Instant.now()),
                Date.from(Instant.now())
                );

        Mockito.when(repository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        Mockito.when(mapper.toCustomerResponseDto(customer)).thenReturn(response);

        CustomerResponseDto expected = service.logIn(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.email(), "amman@example.com");
        Assertions.assertEquals(expected.id(), 1L);
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        Mockito.verify(repository, Mockito.times(1)).findByEmail(customerEmail);
        Mockito.verify(mapper, Mockito.times(1)).toCustomerResponseDto(customer);

        logger.info("Unit Test: Login Credential Test");
    }

    @Test
    public void shouldThrowErrorCustomerNotFoundLoginTest() {
        LoginDto dto = new LoginDto("rizwan@example.com", "amman123@");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "Amman",
                "rizwan@example.com",
                "123456789",
                Date.from(Instant.now()),
                Date.from(Instant.now())
        );

        Mockito.when(repository.findByEmail(dto.email()))
                .thenThrow(new RuntimeException("Customer is not exist! Please SignUp"));

        Mockito.when(mapper.toCustomerResponseDto(customer))
                .thenReturn(response);

        Assertions.assertThrows(RuntimeException.class, () -> service.logIn(dto));

        try {
            CustomerResponseDto expected = service.logIn(dto);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "Customer is not exist! Please SignUp");
        }

        Mockito.verify(repository, Mockito.times(2))
                .findByEmail(dto.email());
        Mockito.verify(mapper, Mockito.times(0))
                .toCustomerResponseDto(customer);

        logger.warn("Unit Test: Error Login Credential Not Found");
    }

    @Test
    public void shouldThrowErrorCustomerIncorrectPasswordLoginTest() {
        LoginDto dto = new LoginDto("amman@example.com", "amman1234");

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
                "123456789",
                Date.from(Instant.now()),
                Date.from(Instant.now())
        );

        Mockito.when(repository.findByEmail(dto.email()))
                .thenReturn(Optional.of(customer));

        Mockito.when(mapper.toCustomerResponseDto(customer))
                .thenReturn(response);

        Assertions.assertThrows(RuntimeException.class, () -> service.logIn(dto));

        try {
            CustomerResponseDto expected = service.logIn(dto);
        }
        catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "Password is incorrect");
        }

        Mockito.verify(repository, Mockito.times(2))
                .findByEmail(dto.email());
        Mockito.verify(mapper, Mockito.times(0))
                .toCustomerResponseDto(customer);

        logger.warn("Unit Test: Error Login with Incorrect Password");
    }

    @Test
    public void shouldReturnCredentialSignUpTest() {
        SignUpDto dto = new SignUpDto(
                "Amman",
                "amman@example.com",
                "123456789",
                "amman123@");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");
        customer.setCreatedAt(Date.from(Instant.now()));
        customer.setUpdatedAt(Date.from(Instant.now()));

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "Amman",
                "amman@example.com",
                "123456789",
                Date.from(Instant.now()),
                Date.from(Instant.now())
        );

        Mockito.when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        Mockito.when(mapper.signUpToCustomer(dto)).thenReturn(customer);
        Mockito.when(repository.save(customer)).thenReturn(customer);
        Mockito.when(mapper.toCustomerResponseDto(customer)).thenReturn(response);

        CustomerResponseDto expected = service.signUp(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.name(), "Amman");
        Assertions.assertEquals(expected.email(), "amman@example.com");
        Assertions.assertNotNull(expected.createdAt());
        Assertions.assertNotNull(expected.updatedAt());

        Mockito.verify(repository, Mockito.times(1)).findByEmail(dto.email());
        Mockito.verify(mapper, Mockito.times(1)).signUpToCustomer(dto);
        Mockito.verify(repository, Mockito.times(1)).save(customer);
        Mockito.verify(mapper, Mockito.times(1)).toCustomerResponseDto(customer);

        logger.info("Unit Test: SignUp Credential Test");
    }

    @Test
    public void shouldThrowErrorCustomerExistSignUpTest() {
        SignUpDto dto = new SignUpDto(
                "Amman",
                "amman@example.com",
                "123456789",
                "amman123@");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");
        customer.setCreatedAt(Date.from(Instant.now()));
        customer.setUpdatedAt(Date.from(Instant.now()));

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "Amman",
                "amman@example.com",
                "123456789",
                Date.from(Instant.now()),
                Date.from(Instant.now())
        );

        Mockito.when(repository.findByEmail(dto.email())).thenReturn(Optional.of(customer));
        Mockito.when(mapper.signUpToCustomer(dto)).thenReturn(customer);
        Mockito.when(repository.save(customer)).thenReturn(customer);
        Mockito.when(mapper.toCustomerResponseDto(customer)).thenReturn(response);

        Assertions.assertThrows(RuntimeException.class, () -> service.signUp(dto));

        try {
            CustomerResponseDto expected = service.signUp(dto);
        }
        catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "Customer already exist!");
        }


        Mockito.verify(repository, Mockito.times(2))
                .findByEmail(dto.email());
        Mockito.verify(mapper, Mockito.times(0))
                .signUpToCustomer(dto);
        Mockito.verify(repository, Mockito.times(0))
                .save(customer);
        Mockito.verify(mapper, Mockito.times(0))
                .toCustomerResponseDto(customer);

        logger.warn("Unit Test: Error SignUp Customer Exists!");
    }
}

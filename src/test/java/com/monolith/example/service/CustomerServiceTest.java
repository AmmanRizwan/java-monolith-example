package com.monolith.example.service;

import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.mapper.CustomerMapper;
import com.monolith.example.model.Customer;
import com.monolith.example.repository.CustomerRepository;
import com.monolith.example.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CustomerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerMapper mapper;
    @Mock
    private CustomerRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateCustomerTest() {
        CustomerDto dto = new CustomerDto(
                "Amman",
                "amman@example.com",
                "12345689",
                "amman123@");

        Customer customer = new Customer();
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        Customer save = new Customer();
        save.setId(1L);
        save.setName("Amman");
        save.setEmail("amman@example.com");
        save.setPhone("123456789");
        save.setPassword("amman123@");

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "Amman",
                "amman@example.com",
                "123456789");

        Mockito.when(mapper.toCustomer(dto)).thenReturn(customer);
        Mockito.when(repository.save(customer)).thenReturn(save);
        Mockito.when(mapper.toCustomerResponseDto(save)).thenReturn(response);

        CustomerResponseDto expected = service.createCustomer(dto);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.id(), 1L);
        Assertions.assertEquals(expected.name(), "Amman");
        Assertions.assertEquals(expected.email(), "amman@example.com");
        Assertions.assertEquals(expected.phone(), "123456789");

        Mockito
                .verify(mapper, Mockito.times(1))
                .toCustomer(dto);
        Mockito
                .verify(repository, Mockito.times(1))
                .save(customer);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toCustomerResponseDto(save);

        logger.info("Unit Test: Create Customer: id={}, name={}", expected.id(), expected.name());
    }

    @Test
    public void shouldReturnListCustomersTest() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "Amman",
                "amman@example.com",
                "123456789");

        Mockito.when(repository.findAll())
                .thenReturn(customers);
        Mockito.when(mapper.toCustomerResponseDto(ArgumentMatchers.any(Customer.class)))
                .thenReturn(response);

        List<CustomerResponseDto> expected = service.findCustomers();

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.size(), 1);

        Mockito
                .verify(repository, Mockito.times(1))
                .findAll();
        Mockito
                .verify(mapper, Mockito.times(1))
                .toCustomerResponseDto(ArgumentMatchers.any(Customer.class));

        logger.info("Unit Test: List of Customer: size={}", expected.size());
    }

    @Test
    public void shouldReturnFindCustomerByIdTest() {
        Long customerId = 1L;

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

        Mockito.when(repository.findById(customerId))
                .thenReturn(Optional.of(customer));
        Mockito.when(mapper.toCustomerResponseDto(customer))
                .thenReturn(response);

        CustomerResponseDto expected = service.findCustomerById(customerId);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.id(), 1L);
        Assertions.assertEquals(expected.name(), "Amman");
        Assertions.assertEquals(expected.email(), "amman@example.com");
        Assertions.assertEquals(expected.phone(), "123456789");

        Mockito
                .verify(repository, Mockito.times(1))
                .findById(customerId);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toCustomerResponseDto(customer);

        logger.info("Unit Test: Find Customer By Id: id={}, name={}", expected.id(), expected.name());
    }

    @Test
    public void shouldThrowErrorFindCustomerByIdTest() {
        Long customerId = 1L;

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

        Mockito.when(repository.findById(customerId))
                .thenThrow(new IllegalArgumentException("cannot find customer by id: " + customerId));
        Mockito.when(mapper.toCustomerResponseDto(customer))
                .thenReturn(response);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findCustomerById(customerId));

        try {
            CustomerResponseDto expected = service.findCustomerById(customerId);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find customer by id: 1");
        }

        // WARN: Mockito.times(2) Due to invoking the service.findCustomerById() 2 times
        // 1. Line 190, 2. Line 193
        Mockito
                .verify(repository, Mockito.times(2))
                .findById(customerId);
        Mockito
                .verify(mapper, Mockito.times(0))
                .toCustomerResponseDto(customer);

        logger.info("Unit Test: Error Find Customer By Id");
    }

    @Test
    public void shouldReturnFindCustomerByEmailTest() {
        String customerEmail = "amman@example.com";

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

        Mockito.when(repository.findByEmail(customerEmail))
                .thenReturn(Optional.of(customer));
        Mockito.when(mapper.toCustomerResponseDto(customer))
                .thenReturn(response);

        CustomerResponseDto expected = service.findCustomerByEmail(customerEmail);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.id(), 1L);
        Assertions.assertEquals(expected.name(), "Amman");
        Assertions.assertEquals(expected.email(), "amman@example.com");
        Assertions.assertEquals(expected.phone(), "123456789");

        Mockito
                .verify(repository, Mockito.times(1))
                .findByEmail(customerEmail);
        Mockito
                .verify(mapper, Mockito.times(1))
                .toCustomerResponseDto(customer);

        logger.info("Unit Test: Find Customer By Email: id={}, email={}", expected.id(), expected.email());
    }

    @Test
    public void shouldThrowErrorFindCustomerByEmailTest() {
        String customerEmail = "amman@example.com";

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

        Mockito.when(repository.findByEmail(customerEmail))
                .thenThrow(new IllegalArgumentException("cannot find customer by email: " + customerEmail));
        Mockito.when(mapper.toCustomerResponseDto(customer))
                .thenReturn(response);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.findCustomerByEmail(customerEmail));

        try {
            CustomerResponseDto expected = service.findCustomerByEmail(customerEmail);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find customer by email: amman@example.com");
        }

        Mockito
                .verify(repository, Mockito.times(2))
                .findByEmail(customerEmail);
        Mockito
                .verify(mapper, Mockito.times(0))
                .toCustomerResponseDto(customer);

        logger.info("Unit Test: Error Find Customer By Email");
    }

    @Test
    public void shouldReturnUpdateCustomerByIdTest() {
        Long customerId = 1L;

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        CustomerDto dto = new CustomerDto(
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123",
                "ammanrizwan123@");

        Customer update = new Customer();
        update.setId(1L);
        update.setName(dto.name());
        update.setEmail(dto.email());
        update.setPhone(dto.phone());
        update.setPassword(dto.password());

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123");

        Mockito.when(repository.findById(customerId))
                .thenReturn(Optional.of(customer));
        Mockito.when(repository.save(ArgumentMatchers.any(Customer.class)))
                .thenReturn(update);
        Mockito.when(mapper.toCustomerResponseDto(update))
                .thenReturn(response);

        CustomerResponseDto expected = service.updateCustomerById(dto, customerId);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.id(), 1L);
        Assertions.assertEquals(expected.name(), "AmmanRizwan");
        Assertions.assertEquals(expected.email(), "ammanrizwan@example.com");
        Assertions.assertEquals(expected.phone(), "789456123");

        Mockito.verify(repository, Mockito.times(1))
                .findById(customerId);
        Mockito.verify(repository, Mockito.times(1))
                .save(customer);
        Mockito.verify(mapper, Mockito.times(1))
                .toCustomerResponseDto(update);

        logger.info("Unit Test: Update Customer By Id: id={}, name={}", expected.id(), expected.name());
    }

    @Test
    public void shouldThrowErrorUpdateCustomerByIdTest() {
        Long customerId = 1L;

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        CustomerDto dto = new CustomerDto(
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123",
                "ammanrizwan123@");

        Customer update = new Customer();
        update.setId(1L);
        update.setName(dto.name());
        update.setEmail(dto.email());
        update.setPhone(dto.phone());
        update.setPassword(dto.password());

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123");

        Mockito.when(repository.findById(customerId))
                .thenThrow(new IllegalArgumentException("cannot find customer by id: " + customerId));
        Mockito.when(repository.save(customer))
                .thenReturn(update);
        Mockito.when(mapper.toCustomerResponseDto(update))
                .thenReturn(response);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.updateCustomerById(dto, customerId));

        try {
            CustomerResponseDto expected = service.updateCustomerById(dto, customerId);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find customer by id: 1");
        }

        Mockito.verify(repository, Mockito.times(2))
                .findById(customerId);
        Mockito.verify(repository, Mockito.times(0))
                .save(customer);
        Mockito.verify(mapper, Mockito.times(0))
                .toCustomerResponseDto(update);

        logger.info("Unit Test: Error Update Customer By Id");
    }

    @Test
    public void shouldReturnUpdateCustomerByEmailTest() {
        String customerEmail = "amman@example.com";

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        CustomerDto dto = new CustomerDto(
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123",
                "ammanrizwan123@");

        Customer update = new Customer();
        update.setId(1L);
        update.setName(dto.name());
        update.setEmail(dto.email());
        update.setPhone(dto.phone());
        update.setPassword(dto.password());

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123");

        Mockito.when(repository.findByEmail(customerEmail))
                .thenReturn(Optional.of(customer));
        Mockito.when(repository.save(ArgumentMatchers.any(Customer.class)))
                .thenReturn(update);
        Mockito.when(mapper.toCustomerResponseDto(update))
                .thenReturn(response);

        CustomerResponseDto expected = service.updateCustomerByEmail(dto, customerEmail);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.id(), 1L);
        Assertions.assertEquals(expected.name(), "AmmanRizwan");
        Assertions.assertEquals(expected.email(), "ammanrizwan@example.com");
        Assertions.assertEquals(expected.phone(), "789456123");

        Mockito.verify(repository, Mockito.times(1))
                .findByEmail(customerEmail);
        Mockito.verify(repository, Mockito.times(1))
                .save(customer);
        Mockito.verify(mapper, Mockito.times(1))
                .toCustomerResponseDto(update);

        logger.info("Unit Test: Update Customer By Email: id={}, email={}", expected.id(), expected.email());
    }

    @Test
    public void shouldThrowErrorUpdateCustomerByEmailTest() {
        String customerEmail = "amman@example.com";

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Amman");
        customer.setEmail("amman@example.com");
        customer.setPhone("123456789");
        customer.setPassword("amman123@");

        CustomerDto dto = new CustomerDto(
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123",
                "ammanrizwan123@");

        Customer update = new Customer();
        update.setId(1L);
        update.setName(dto.name());
        update.setEmail(dto.email());
        update.setPhone(dto.phone());
        update.setPassword(dto.password());

        CustomerResponseDto response = new CustomerResponseDto(
                1L,
                "AmmanRizwan",
                "ammanrizwan@example.com",
                "789456123");

        Mockito.when(repository.findByEmail(customerEmail))
                .thenThrow(new IllegalArgumentException("cannot find customer by id: " + customerEmail));
        Mockito.when(repository.save(customer))
                .thenReturn(update);
        Mockito.when(mapper.toCustomerResponseDto(update))
                .thenReturn(response);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.updateCustomerByEmail(dto, customerEmail));

        try {
            CustomerResponseDto expected = service.updateCustomerByEmail(dto, customerEmail);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(), "cannot find customer by id: amman@example.com");
        }

        Mockito.verify(repository, Mockito.times(2))
                .findByEmail(customerEmail);
        Mockito.verify(repository, Mockito.times(0))
                .save(customer);
        Mockito.verify(mapper, Mockito.times(0))
                .toCustomerResponseDto(update);

        logger.info("Unit Test: Error Update Customer By Email");
    }
}

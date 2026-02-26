package com.monolith.example.services.impl;

import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.mapper.CustomerMapper;
import com.monolith.example.model.Customer;
import com.monolith.example.repository.CustomerRepository;
import com.monolith.example.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private CustomerRepository repository;

    @Override
    public CustomerResponseDto createCustomer(CustomerDto dto) {
        Customer customer = mapper.toCustomer(dto);
        Customer save = repository.save(customer);
        return mapper.toCustomerResponseDto(save);
    }

    @Override
    public List<CustomerResponseDto> findCustomers() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toCustomerResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto findCustomerById(Long id) {
        Customer customer = repository
                                .findById(id)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("cannot find customer by id: " + id)
                                );

        return mapper.toCustomerResponseDto(customer);
    }

    @Override
    public CustomerResponseDto findCustomerByEmail(String email) {
        Customer customer = repository
                .findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("cannot find customer by email: " + email)
                );

        return mapper.toCustomerResponseDto(customer);
    }

    @Override
    public CustomerResponseDto updateCustomerById(CustomerDto dto, Long id) {
        Customer customer = repository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("cannot find customer by id: " + id)
                );

        customer.setName(dto.name() == null ? customer.getName() : dto.name());
        customer.setEmail(dto.email() == null ? customer.getEmail() : dto.email());
        customer.setPhone(dto.phone() == null ? customer.getPhone() : dto.phone());

        Customer updateCustomer = repository.save(customer);

        return mapper.toCustomerResponseDto(updateCustomer);
    }

    @Override
    public CustomerResponseDto updateCustomerByEmail(CustomerDto dto, String email) {
        Customer customer = repository
                .findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("cannot find customer by email: " + email)
                );

        customer.setName(dto.name() == null ? customer.getName() : dto.name());
        customer.setEmail(dto.email() == null ? customer.getEmail() : dto.email());
        customer.setPhone(dto.phone() == null ? customer.getPhone() : dto.phone());

        Customer updateCustomer = repository.save(customer);

        return mapper.toCustomerResponseDto(updateCustomer);
    }

    @Override
    public void deleteCustomerById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        Customer customer = repository
                .findByEmail(email)
                .orElseThrow(
                () -> new IllegalArgumentException("cannot find customer by email: " + email)
                );

        repository.delete(customer);
    }
}

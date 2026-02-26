package com.monolith.example.services.impl;

import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.dto.LoginDto;
import com.monolith.example.dto.SignUpDto;
import com.monolith.example.mapper.CustomerMapper;
import com.monolith.example.model.Customer;
import com.monolith.example.repository.CustomerRepository;
import com.monolith.example.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerMapper mapper;

    @Override
    public CustomerResponseDto logIn(LoginDto dto) {
        Optional<Customer> existCustomer = repository.findByEmail(dto.email());

        if (existCustomer.isEmpty()) {
            throw new RuntimeException("Customer is not exist! Please SignUp");
        }

        if (!existCustomer.get().getPassword().equals(dto.password())) {
            throw new RuntimeException("Password is incorrect");
        }

        // Implementation need to be change
        // Just for example
        Customer customer = existCustomer.get();

        return mapper.toCustomerResponseDto(customer);
    }

    @Override
    public CustomerResponseDto signUp(SignUpDto dto) {
        Optional<Customer> existCustomer = repository.findByEmail(dto.email());

        if (existCustomer.isPresent()) {
            throw new RuntimeException("Customer already exist!");
        }

        Customer customer = mapper.signUpToCustomer(dto);
        Customer save = repository.save(customer);

        return mapper.toCustomerResponseDto(save);
    }
}

package com.monolith.example.mapper;

import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.dto.SignUpDto;
import com.monolith.example.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());
        customer.setPassword(dto.password());

        return customer;
    }

    public Customer signUpToCustomer(SignUpDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());
        customer.setPassword(dto.password());

        return customer;
    }

    public CustomerResponseDto toCustomerResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}

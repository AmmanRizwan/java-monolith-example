package com.monolith.example.services;

import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerDto dto);
    List<CustomerResponseDto> findCustomers();
    CustomerResponseDto findCustomerById(Long id);
    CustomerResponseDto findCustomerByEmail(String email);
    CustomerResponseDto updateCustomerById(CustomerDto dto, Long id);
    CustomerResponseDto updateCustomerByEmail(CustomerDto dto, String email);
    void deleteCustomerById(Long id);
    void deleteCustomerByEmail(String email);
}

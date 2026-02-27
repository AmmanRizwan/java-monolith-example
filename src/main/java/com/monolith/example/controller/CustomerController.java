package com.monolith.example.controller;

import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.response.ApiResponse;
import com.monolith.example.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDto>> createCustomer(
            @RequestBody CustomerDto dto
            ) {
        var data = customerService.createCustomer(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Customer created Successfully", data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> findCustomers() {
        var data = customerService.findCustomers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Customers fetched Successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> findCustomer(
            @PathVariable("id") Long id
    ) {
        var data = customerService.findCustomerById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer fetched Successfully", data));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> findCustomerByEmail(
            @PathVariable("email") String email
    ) {
        var data = customerService.findCustomerByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer fetched Successfully", data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(
            @RequestBody CustomerDto dto,
            @PathVariable("id") Long id
    ) {
        customerService.updateCustomerById(dto, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer updated Successfully", null));
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomerByEmail(
            @RequestBody CustomerDto dto,
            @PathVariable("email") String email
    ) {
        customerService.updateCustomerByEmail(dto, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer updated Successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> deleteCustomer(
            @PathVariable("id") Long id
    ) {
        customerService.deleteCustomerById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer deleted Successfully", null));
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> deleteCustomerByEmail(
            @PathVariable("email") String email
    ) {
        customerService.deleteCustomerByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Customer deleted Successfully", null));
    }
}

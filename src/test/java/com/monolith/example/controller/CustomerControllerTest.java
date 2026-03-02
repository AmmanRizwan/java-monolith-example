package com.monolith.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolith.example.dto.CustomerDto;
import com.monolith.example.dto.CustomerResponseDto;
import com.monolith.example.response.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateCustomerTest() throws Exception {
        CustomerDto dto = new CustomerDto(
                "john",
                "john@example.com",
                "789456123",
                "john123@");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Customer created Successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name")
                        .value("john"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdAt")
                        .exists())
                .andReturn();

        ApiResponse<CustomerResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ApiResponse<CustomerResponseDto>>() {}
        );

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData().id());
        Assertions.assertEquals(response.getData().name(), "john");
        Assertions.assertNotNull(response.getData().createdAt());
        Assertions.assertNotNull(response.getData().updatedAt());

        logger.info("Integration Test: Create Customer: id={}, name={}", response.getData().id(), response.getData().name());
    }
}

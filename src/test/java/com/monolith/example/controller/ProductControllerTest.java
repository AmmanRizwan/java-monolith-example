package com.monolith.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.response.ApiResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateProductTest() throws Exception {
        ProductDto dto = new ProductDto(
                "Microwave",
                new BigDecimal("2.4"),
                "This is a simple microwave");

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/v1/api/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Product created Successfully"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.id")
                        .exists())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.name")
                        .value("Microwave"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.price")
                        .value(Matchers.any(Double.class)));
    }

    @Test
    public void shouldReturnListProductsTest() throws Exception {
        MvcResult result = mockMvc
                .perform(
                MockMvcRequestBuilders
                        .get("/v1/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Products fetched Successfully"))
                .andReturn();

        ApiResponse<List<ProductResponseDto>> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ApiResponse<List<ProductResponseDto>>>() {}
        );

        Assertions.assertEquals(response.getMessage(), "Products fetched Successfully");
        Assertions.assertNotEquals(response.getData().size(), 0);
        Assertions.assertEquals(response.getData().size(), 5);
    }

    @Test
    public void shouldReturnProductTest() throws Exception {
        Long productId = 1L;

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/api/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Product fetched Successfully"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.name")
                        .value("Laptop"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.description")
                        .value("This is a simple laptop"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.id")
                        .exists())
                .andReturn();

        ApiResponse<ProductResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ApiResponse<ProductResponseDto>>() {}
        );

        Assertions.assertEquals(response.getMessage(), "Product fetched Successfully");
        Assertions.assertEquals(response.getData().id(), productId);
        Assertions.assertEquals(response.getData(), "Laptop");
    }
}

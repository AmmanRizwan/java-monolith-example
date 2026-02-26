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
                        .value(Matchers.any(Double.class)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
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
                .andExpect(MockMvcResultMatchers.status().isOk())
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
    public void shouldReturnProductByIdTest() throws Exception {
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ApiResponse<ProductResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ApiResponse<ProductResponseDto>>() {}
        );

        Assertions.assertEquals(response.getMessage(), "Product fetched Successfully");
        Assertions.assertEquals(response.getData().id(), productId);
        Assertions.assertEquals(response.getData().name(), "Laptop");
        Assertions.assertNotNull(response.getData());
    }

    @Test
    public void shouldReturnProductByNameTest() throws Exception {
        String productName = "Phone";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/api/product/name/{name}", productName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Product fetched Successfully"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.name")
                        .value("Phone"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.description")
                        .value("This is a simple phone"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.id")
                        .exists())
                .andReturn();

        ApiResponse<ProductResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ApiResponse<ProductResponseDto>>() {}
        );

        Assertions.assertEquals(response.getMessage(), "Product fetched Successfully");
        Assertions.assertEquals(response.getData().name(), productName);
        Assertions.assertEquals(response.getData().id(), 2L);
    }

    @Test
    public void shouldReturnUpdateProductByIdTest() throws Exception {

    }

    @Test
    public void shouldReturnUpdateProductByNameTest() throws Exception {

    }

    @Test
    public void shouldRemoveProductByIdTest() throws Exception {

    }

    @Test
    public void shouldRemoveProductByNameTest() throws Exception {

    }

}

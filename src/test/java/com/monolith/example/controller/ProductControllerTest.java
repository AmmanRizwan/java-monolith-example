package com.monolith.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolith.example.dto.ProductDto;
import com.monolith.example.dto.ProductResponseDto;
import com.monolith.example.response.ApiResponse;
import org.hamcrest.Matchers;
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

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

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

        MvcResult result = mockMvc
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
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        ApiResponse<ProductResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<ApiResponse<ProductResponseDto>>() {}
        );

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData().id());
        Assertions.assertEquals(response.getData().name(), "Microwave");
        Assertions.assertNotNull(response.getData().createdAt());
        Assertions.assertNotNull(response.getData().updatedAt());

        logger.info("Integration Test: Create Product: id={}, name={}", response.getData().id(), response.getData().name());
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
        Assertions.assertEquals(response.getData().size(), 6);

        logger.info("Integration Test: List Products size={}", response.getData().size());
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
        Assertions.assertNotNull(response.getData().createdAt());
        Assertions.assertNotNull(response.getData().updatedAt());

        logger.info("Integration Test: Get Product By Id: id={}, name={}", response.getData().id(), response.getData().name());
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
        Assertions.assertNotNull(response.getData().createdAt());
        Assertions.assertNotNull(response.getData().updatedAt());

        logger.info("Integration Test: Get Product By Name: id={}, name={}", response.getData().id(), response.getData().name());
    }

    @Test
    public void shouldReturnUpdateProductByIdTest() throws Exception {
        Long productId = 4L;

        ProductDto updateProduct = new ProductDto(
                "Samsung Tablet",
                new BigDecimal("10.12"),
                "This is a Samsung tablet");

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/v1/api/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProduct)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Product updated Successfully"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ApiResponse<ProductResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ApiResponse<ProductResponseDto>>() {}
        );

        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getMessage(), "Product updated Successfully");

        logger.info("Integration Test: Update Product By Id");
    }

    @Test
    public void shouldReturnUpdateProductByNameTest() throws Exception {
        String productName = "Camera";

        ProductDto updateProduct = new ProductDto(
                "SONY Camera",
                new BigDecimal("20.2"),
                "This is an SONY Camera");

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/v1/api/product/name/{name}", productName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProduct)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Product updated Successfully"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ApiResponse<ProductResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ApiResponse<ProductResponseDto>>() {}
        );

        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getMessage(), "Product updated Successfully");

        logger.info("Integration Test: Update Product By Name");
    }

    @Test
    public void shouldRemoveProductByIdTest() throws Exception {
        Long productId = 3L;

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/api/product/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Product deleted Successfully"))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk());

        logger.info("Integration Test: Delete Product By Id");
    }

    @Test
    public void shouldRemoveProductByNameTest() throws Exception {
        String productName = "Cable";

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/api/product/name/{name}", productName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Product deleted Successfully"));

        logger.info("Integration Test: Delete Product By Name");
    }
}

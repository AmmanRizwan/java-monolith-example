package com.monolith.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolith.example.response.ApiResponse;
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

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnIsOkResponseTest() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Server is running!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("OK"))
                .andReturn();

        ApiResponse<String> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<ApiResponse<String>>() {}
        );

        Assertions.assertEquals(response.getMessage(), "Server is running!");
        Assertions.assertEquals(response.getData(), "OK");
    }
}

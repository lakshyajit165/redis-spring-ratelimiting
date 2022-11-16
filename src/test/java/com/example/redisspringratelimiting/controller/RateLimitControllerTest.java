package com.example.redisspringratelimiting.controller;

import com.example.redisspringratelimiting.config.RateLimitConfig;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class RateLimitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RateLimitConfig rateLimitConfig;

    @Test
    public void getInfo() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/user/1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals(result.getResponse().getStatus(), 200);

    }

}
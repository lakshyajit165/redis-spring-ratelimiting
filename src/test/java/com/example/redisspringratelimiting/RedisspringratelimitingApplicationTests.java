package com.example.redisspringratelimiting;

import com.example.redisspringratelimiting.config.RateLimitConfig;
import com.example.redisspringratelimiting.config.RedisConfig;

import com.example.redisspringratelimiting.model.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RedisspringratelimitingApplicationTests {

    /**
     * Below mock beans are needed for initializing application context
     * */
    @MockBean
    RateLimitConfig rateLimitConfig;

    @MockBean
    RedisConfig redisConfig;

    /**
     * Below tests work when the application is already running.
     * */

    @Test
    public void testAPIWithoutRateLimit() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8085 + "/user/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<?> result = restTemplate.getForEntity(uri, String.class);
        // verify response status
        assertEquals(200, result.getStatusCodeValue());
        // verify response body
        assertEquals("{\"message\":\"Success for user 1\"}", result.getBody());
    }

    @Test
    public void testAPIWithRateLimitExceeding() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8085 + "/user/2";
        URI uri = new URI(baseUrl);
        for(int i = 0; i < 20; i++) {
            restTemplate.getForEntity(uri, String.class);
        }
        try {
            ResponseEntity<?> result = restTemplate.getForEntity(uri, String.class);
        } catch(HttpClientErrorException ex) {
            // verify response status
            assertEquals(429, ex.getRawStatusCode());
            // verify response body
            assertEquals("{\"message\":\"Rate limit exceeded for user 2\"}", ex.getResponseBodyAsString());
        }
    }
}

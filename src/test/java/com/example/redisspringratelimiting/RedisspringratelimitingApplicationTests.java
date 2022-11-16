package com.example.redisspringratelimiting;

import com.example.redisspringratelimiting.config.RateLimitConfig;
import com.example.redisspringratelimiting.config.RedisConfig;
import com.example.redisspringratelimiting.controller.RateLimitController;
import com.giffing.bucket4j.spring.boot.starter.config.cache.SyncCacheResolver;
import com.giffing.bucket4j.spring.boot.starter.config.cache.jcache.JCacheCacheResolver;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class RedisspringratelimitingApplicationTests {

    @MockBean
    RateLimitConfig rateLimitConfig;

    @MockBean
    RedisConfig redisConfig;

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    RateLimitController rateLimitController;

    @Test
    public void testAPI() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/user/1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);

//        assertEquals(result., "Hello 1");
    }
}

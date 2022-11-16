package com.example.redisspringratelimiting.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class RateLimitConfig {
    //autowiring dependencies

    @Autowired
    public ProxyManager buckets;

    public Bucket resolveBucket(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(key);

        // Does not always create a new bucket, but instead returns the existing one if it exists.
        return buckets.builder().build(key, configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(String key) {
        //User user = userRepository.findById(userId);
        Refill refill = Refill.intervally(20, Duration.ofMinutes(1));
        //create 10 token bandwidth
        Bandwidth limit = Bandwidth.classic(20, refill);

        // Bandwidth limit = Bandwidth.

        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}

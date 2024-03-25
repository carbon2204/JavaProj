package com.example.demo.caches;

import com.example.demo.service.CacheService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheService cacheService() {
        return new CacheService();
    }
}
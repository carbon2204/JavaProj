package com.example.demo.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CacheServiceTest {

    @Test
    public void testPutAndGet() {
        // Arrange
        CacheService cacheService = new CacheService();
        String key = "testKey";
        Object value = "testValue";

        // Act
        cacheService.put(key, value);
        Object retrievedValue = cacheService.get(key);

        // Assert
        assertEquals(value, retrievedValue);
    }

    @Test
    public void testRemove() {
        // Arrange
        CacheService cacheService = new CacheService();
        String key = "testKey";
        Object value = "testValue";

        // Act
        cacheService.put(key, value);
        cacheService.remove(key);
        boolean containsKey = cacheService.containsKey(key);

        // Assert
        assertFalse(containsKey);
    }

    @Test
    public void testContainsKey() {
        // Arrange
        CacheService cacheService = new CacheService();
        String key = "testKey";
        Object value = "testValue";

        // Act
        cacheService.put(key, value);
        boolean containsKey = cacheService.containsKey(key);

        // Assert
        assertTrue(containsKey);
    }
}


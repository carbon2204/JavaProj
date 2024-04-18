package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CacheServiceTest {

  @Test
  void testPutAndGet() {
    CacheService cacheService = new CacheService();
    String key = "testKey";
    Object value = "testValue";

    cacheService.put(key, value);
    Object retrievedValue = cacheService.get(key);

    assertEquals(value, retrievedValue);
  }

  @Test
  void testRemove() {
    CacheService cacheService = new CacheService();
    String key = "testKey";
    Object value = "testValue";

    cacheService.put(key, value);
    cacheService.remove(key);
    boolean containsKey = cacheService.containsKey(key);

    assertFalse(containsKey);
  }

  @Test
  void testContainsKey() {
    CacheService cacheService = new CacheService();
    String key = "testKey";
    Object value = "testValue";

    cacheService.put(key, value);
    boolean containsKey = cacheService.containsKey(key);

    assertTrue(containsKey);
  }
}


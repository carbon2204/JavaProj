package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class CacheService {

    private final Map<String, Object> cache = new LinkedHashMap<>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Object> eldest){
            return size()>10;
        }
    };

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void remove(String key){cache.remove(key);}

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
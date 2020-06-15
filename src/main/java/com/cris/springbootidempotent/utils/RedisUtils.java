package com.cris.springbootidempotent.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate template;

    public boolean delKey(String key) {
        return template.delete(key);
    }

    public boolean hasKey(String key) {
        return template.hasKey(key);
    }

    public void addKey(String key) {
        template.opsForValue().set(key, key);
    }
}

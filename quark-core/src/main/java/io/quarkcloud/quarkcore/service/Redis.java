package io.quarkcloud.quarkcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class Redis {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    public void setKeyValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
 
    public Object getValueByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
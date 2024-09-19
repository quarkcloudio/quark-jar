package io.quarkcloud.quarkcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class Redis {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
 
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getValueAndDelete(String key) {
        // 读取值
        Object value = redisTemplate.opsForValue().get(key);
        
        // 删除键
        redisTemplate.delete(key);
        
        return value;
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
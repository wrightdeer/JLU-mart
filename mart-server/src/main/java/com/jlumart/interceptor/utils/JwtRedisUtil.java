package com.jlumart.interceptor.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Data
public class JwtRedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String JWT_KEY_PREFIX = "JWT:";
    /**
     * 将jwt令牌存储到redis中
     * @param userId
     * @param jwt
     */
    public void saveJwtToRedis(String userId, String jwt, long timeout) {
        String jwtKey = JWT_KEY_PREFIX + userId+ ":" + jwt;

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(jwtKey, "", timeout, TimeUnit.SECONDS);
    }

    /**
     * 从redis中删除指定用户的jwt令牌
     * @param userId
     */
    public void removeJwtFromRedis(String userId) {
        String jwtKeyPrefix = JWT_KEY_PREFIX + userId + ":";
        Set<String> keys = redisTemplate.keys(jwtKeyPrefix + "*");
        redisTemplate.delete(keys);
    }

    /**
     * 从redis中删除指定用户的指定jwt令牌
     * @param userId
     * @param jwt
     */
    public void removeJwtFromRedis(String userId, String jwt) {
        String jwtKey = JWT_KEY_PREFIX + userId+ ":" + jwt;
        redisTemplate.delete(jwtKey);
    }

    /**
     * 检查redis中是否有指定用户的指定jwt令牌
     * @param userId
     * @param jwt
     */
    public boolean checkJwtInRedis(String userId, String jwt) {
        String jwtKey = JWT_KEY_PREFIX + userId+ ":" + jwt;
        return redisTemplate.hasKey(jwtKey);
    }
}

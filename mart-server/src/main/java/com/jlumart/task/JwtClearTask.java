package com.jlumart.task;

import com.jlumart.constant.JwtClaimsConstant;
import com.jlumart.interceptor.utils.JwtRedisUtil;
import com.jlumart.properties.JwtProperties;
import com.jlumart.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class JwtClearTask {
    @Autowired
    private JwtRedisUtil jwtRedisUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtProperties jwtProperties;
    private static final String JWT_KEY = "jwt:";
    @Scheduled(cron = "* * 0 * * ? ") // 每天0点执行一次
    public void execute() {
        log.info("清理过期令牌");
        Set<String> keys = redisTemplate.keys(JWT_KEY + "*");
        for (String key : keys) {
            checkJwt(key);
        }
    }

    private void checkJwt(String key) {
        String token = key.split(":")[3];
        Long userId = Long.valueOf(key.split(":")[2]);
        String identity = key.split(":")[1];

        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long Id = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            if (!Id.equals(userId)) {
                jwtRedisUtil.removeJwtFromRedis(identity+":"+userId, token);
            }
        } catch (Exception e) {
            jwtRedisUtil.removeJwtFromRedis(identity+":"+userId, token);
        }
    }
}

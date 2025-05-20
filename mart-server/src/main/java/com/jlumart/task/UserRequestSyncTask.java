package com.jlumart.task;

import com.jlumart.entity.User;
import com.jlumart.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class UserRequestSyncTask {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String REQUEST_COUNT_PREFIX = "USER:REQUEST:COUNT:";
    private static final String REQUEST_TIME_PREFIX = "USER:REQUEST:TIME:";
    @Scheduled(cron = "0 0 0 * * ? ")
    public void execute() {
        log.info("同步用户请求统计数据");

        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 使用SCAN替代KEYS
        Cursor<String> keys = redisTemplate.scan(
                ScanOptions.scanOptions()
                        .match(REQUEST_COUNT_PREFIX + "*")
                        .count(100) // 分批大小
                        .build()
        );

        while (keys.hasNext()) {
            Long userId = null;
            try {
                String key = keys.next();
                userId = Long.parseLong(key.split(":")[3]);
                String timeKey = REQUEST_TIME_PREFIX + userId;

                User user = User.builder()
                        .id(userId)
                        .activeDays(1L)
                        .build();

                // 使用Optional避免NPE
                Optional.ofNullable(redisTemplate.opsForValue().get(timeKey))
                        .ifPresent(time -> user.setLastRequestTime((LocalDateTime) time));

                // 使用泛型避免类型转换警告
                redisTemplate.setValueSerializer(stringRedisSerializer);
                String countStr = (String) redisTemplate.opsForValue().get(key);
                redisTemplate.setValueSerializer(valueSerializer);

                Long count = Long.parseLong(countStr);
                user.setRequestCount(count);

                userMapper.update(user);

                redisTemplate.delete(key);
                redisTemplate.delete(timeKey);

            } catch (Exception e) {
                log.error("用户{}数据同步失败: {}", userId, e.getMessage());
            }
        }
    }

}

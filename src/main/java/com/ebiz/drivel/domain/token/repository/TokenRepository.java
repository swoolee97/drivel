package com.ebiz.drivel.domain.token.repository;

import com.ebiz.drivel.domain.auth.application.JwtProvider;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
    private final long REFRESH_TOKEN_EXPIRE = JwtProvider.REFRESH_TOKEN_EXPIRE;
    private final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOperations;

    public TokenRepository(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void save(Long memberId, String refreshToken) {
        valueOperations.set(REFRESH_TOKEN_PREFIX + memberId, refreshToken);
        redisTemplate.expire(REFRESH_TOKEN_PREFIX + memberId, REFRESH_TOKEN_EXPIRE, TimeUnit.SECONDS);
    }

    public void delete(Long memberId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + memberId);
    }

    public String findById(Long memberId) {
        return valueOperations.get(REFRESH_TOKEN_PREFIX + memberId);
    }

    public void deleteById(Long memberId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + memberId);
    }

}

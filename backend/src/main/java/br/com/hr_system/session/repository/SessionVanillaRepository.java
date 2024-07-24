package br.com.hr_system.session.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SessionVanillaRepository {


    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SessionVanillaRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<String> findByUserIdIn(List<String> userId) {

        return userId.stream()
                .map(id -> redisTemplate.opsForSet().members("Session:userId:"+id))
                .flatMap(set -> set.stream().map(Object::toString))
                .toList();
    }

    public Integer findUserIdBySessionId(String sessionId) {
        return Integer.valueOf((String) redisTemplate.opsForHash().get("Session:"+sessionId, "userId"));
    }
}

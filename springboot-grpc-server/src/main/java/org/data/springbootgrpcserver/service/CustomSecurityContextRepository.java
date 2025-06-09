package org.data.springbootgrpcserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *  自定义会话管理 存储redis中 以 key 为 "security:context:" + sessionId;  value 为 SecurityContext
 */
@Component
public class CustomSecurityContextRepository implements SecurityContextRepository {


    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.cache.redis.time-to-live}")
    private long expirationTime;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();

        String sessionId = request.getSession().getId();
        if(sessionId == null) {
            return SecurityContextHolder.createEmptyContext();

        }
        String redisKey = "security:context:" + sessionId;
        Object context = redisService.get(redisKey);
//        SecurityContextImpl context = (SecurityContextImpl) redisTemplate.opsForValue().get(redisKey);
//        Object context = redisTemplate.opsForValue().get(redisKey);
        if (context == null) {
            return SecurityContextHolder.createEmptyContext();
        }
        try {
            return objectMapper.readValue((String) context, SecurityContext.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

        /**
         * 存储会话管理，通过sessionId
         */
        String sessionId = request.getSession().getId();
        if(sessionId == null || context.getAuthentication() == null) return;

        String redisKey = "security:context:" + sessionId;

        try {
            String contextJson = objectMapper.writeValueAsString(context);
//            redisTemplate.opsForValue().set(redisKey,contextJson, expirationTime, TimeUnit.SECONDS);
            redisService.setWithExpire(redisKey, contextJson,expirationTime, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


//        redisTemplate.opsForValue().set(redisKey, securityContextImpl, expirationTime, TimeUnit.SECONDS);

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {

        String sessionId = request.getSession().getId();
        if(sessionId == null) return false;

        String redisKey = "security:context:" + sessionId;
        Object value = redisService.get(redisKey);
        if(value == null) {
            return  false;
        }
        SecurityContext context  = (SecurityContext) value;
        return true;
    }
}

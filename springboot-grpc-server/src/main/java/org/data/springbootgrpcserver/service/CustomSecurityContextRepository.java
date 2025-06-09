package org.data.springbootgrpcserver.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Value("${spring.cache.redis.time-to-live}")
    private long expirationTime;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();

        String sessionId = request.getSession().getId();
        if(sessionId == null) {
            return SecurityContextHolder.createEmptyContext();

        }
        String redisKey = "security:context:" + sessionId;
        boolean isExist = redisService.hasKey(redisKey);
        if (isExist) {
            SecurityContext context = (SecurityContext) redisService.get(redisKey);
            return  context ;
        }else  {
            return SecurityContextHolder.createEmptyContext();
        }

    }


    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

        /**
         * 存储会话管理，通过sessionId
         */
        System.out.println("saveContext  "+request.getSession().getId());
        String sessionId = request.getSession().getId();
       if(sessionId == null || context.getAuthentication() == null) return;

        String redisKey = "security:context:" + sessionId;

        System.out.println(redisKey+"======>"+context);

        redisService.setWithExpire(redisKey,context, expirationTime, TimeUnit.SECONDS);

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

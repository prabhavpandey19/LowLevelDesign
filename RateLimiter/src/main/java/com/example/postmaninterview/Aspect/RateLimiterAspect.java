package com.example.postmaninterview.Aspect;

import com.example.postmaninterview.utils.RateLimiter;
import com.example.postmaninterview.utils.RateLimiterFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimiterAspect {

    @Value("#{'${rate.limiter.duration}'}")
    private Long duration;

    @Value("#{'${rate.limiter.allowedRequestNumber}'}")
    private Long allowedRequestNumber;

    @Value("#{'${rate.limiter.alog}'}")
    private String alogrithm;

    private ConcurrentHashMap<String, RateLimiter> rateLimiterConcurrentHashMap = new ConcurrentHashMap<>();

    @Around("@annotation(com.example.postmaninterview.Annotations.Ratelimit)")
    public Object Authorization(ProceedingJoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        rateLimiterConcurrentHashMap.putIfAbsent(key, getRateLimiterObject());
        if (rateLimiterConcurrentHashMap.get(key).allowRequest()) {
            return joinPoint.proceed();
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"Allowed Reqeust exceded");
        }
    }

    private RateLimiter getRateLimiterObject() {
        return RateLimiterFactory.getRateLimiterObject(alogrithm, duration, allowedRequestNumber);
    }

}

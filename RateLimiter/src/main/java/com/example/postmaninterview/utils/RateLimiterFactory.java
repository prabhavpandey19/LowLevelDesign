package com.example.postmaninterview.utils;

public class RateLimiterFactory {


    public static RateLimiter getRateLimiterObject(String alog, long duration, long allowedRequestNumber) {
        if (alog.equals("FixedWindow")) {
            return new FixedWindowRateLimiter(duration, allowedRequestNumber);
        } else if (alog.equals("SlidingWindow")) {
            return new SlidingWindowRateLimiter(duration, allowedRequestNumber);
        }
        throw new IllegalArgumentException("Invalid RateLimiter Algorithm");
    }
}

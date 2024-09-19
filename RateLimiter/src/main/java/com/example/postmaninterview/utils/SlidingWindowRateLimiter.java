package com.example.postmaninterview.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final long maxRequests;
    private final long windowSizeInMillis;
    private final Queue<Long> requestTimestamps;

    public SlidingWindowRateLimiter(long windowSizeInMillis, long maxRequests) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.requestTimestamps = new LinkedList<>();
    }
    @Override
    public boolean allowRequest() {
        long currentTime = System.currentTimeMillis();
        while (!requestTimestamps.isEmpty() && currentTime - requestTimestamps.peek() > windowSizeInMillis) {
            requestTimestamps.poll();
        }
        if (requestTimestamps.size() < maxRequests) {
            requestTimestamps.add(currentTime);
            return true;
        }
        return false;
    }
}

package com.example.postmaninterview.utils;

public class FixedWindowRateLimiter implements RateLimiter {

    private Long duration;
    private Long allowedRequestNumber;
    private Long requestCounter;
    private Long initialRequestTime;

    public FixedWindowRateLimiter(Long duration, Long allowedRequestNumber) {
        this.duration = duration;
        this.allowedRequestNumber = allowedRequestNumber;
        this.requestCounter = 0L;
        this.initialRequestTime = System.currentTimeMillis();
    }



    @Override
    public boolean allowRequest() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - initialRequestTime >= duration) {
            initialRequestTime = currentTime;
            requestCounter = 0L;
        }
        requestCounter++;
        if (requestCounter <= allowedRequestNumber) {
            return true;
        }
        return false;
    }
}

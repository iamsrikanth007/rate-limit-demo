package com.shareknowledge.ratelimitingdemo.exception;

public class RateLimitExceededException extends RuntimeException {

    public RateLimitExceededException(String tenantName, long minutes, long seconds) {
        super(String.format("%s rate limit exceeded, Please wait for %d minutes and %d seconds to access the apis",
                tenantName, minutes, seconds));
    }
}

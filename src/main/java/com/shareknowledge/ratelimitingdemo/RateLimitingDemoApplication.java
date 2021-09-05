package com.shareknowledge.ratelimitingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RateLimitingDemoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RateLimitingDemoApplication.class, args);
    }

    @Bean
    public RestTemplate localApiClient() {
        return new RestTemplate();
    }
}

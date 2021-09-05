package com.shareknowledge.ratelimitingdemo.config;

import com.shareknowledge.ratelimitingdemo.entity.Tenant;
import com.shareknowledge.ratelimitingdemo.exception.RateLimitExceededException;
import com.shareknowledge.ratelimitingdemo.exception.TenantNotFoundException;
import com.shareknowledge.ratelimitingdemo.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Autowired
    private TenantRepository tenantRepository;

    @Value("${api.ratelimit}")
    private int apiRateLimit;

    @Value("${api.request.time.window}")
    private int apiRequestTimeWindow;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("/joke")) {
            String tenantName = request.getParameter("tenantName");
            if (StringUtils.hasText(tenantName)) {
                validateRateLimit(tenantName);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    public void validateRateLimit(String tenantName) {
        Optional<Tenant> tenantOptional = tenantRepository.findByName(tenantName.trim().toUpperCase());
        if (tenantOptional.isPresent()) {
            Tenant tenant = tenantOptional.get();
            Date now = new Date();
            Date timeWindowStartedOn = tenant.getTimeWindowStartedOn();
            if ((now.getTime() - timeWindowStartedOn.getTime()) / (1000) >= apiRequestTimeWindow * 60) {
                tenant.setTimeWindowStartedOn(now);
                tenant.setRequestsInTimeWindow(1);
                tenant.setTotalRequests(tenant.getTotalRequests() + 1);
            } else {
                if (tenant.getRequestsInTimeWindow() >= apiRateLimit) {
                    Date timeWindowEndsOn = new Date(timeWindowStartedOn.getTime() + apiRequestTimeWindow * 60 * 1000);
                    long waitTimeInSeconds = (timeWindowEndsOn.getTime() - now.getTime()) / (1000);
                    long waitTimeInMinutes = waitTimeInSeconds / 60;
                    long remainingSecondsOfWaitTime = waitTimeInSeconds % 60;
                    throw new RateLimitExceededException(tenantName, waitTimeInMinutes, remainingSecondsOfWaitTime);
                } else {
                    tenant.setRequestsInTimeWindow(tenant.getRequestsInTimeWindow() + 1);
                    tenant.setTotalRequests(tenant.getTotalRequests() + 1);
                }
            }
            tenantRepository.save(tenant);
        } else {
            throw new TenantNotFoundException(tenantName);
        }
    }
}

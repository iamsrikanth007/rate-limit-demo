package com.shareknowledge.ratelimitingdemo.service;

import com.shareknowledge.ratelimitingdemo.entity.Tenant;
import com.shareknowledge.ratelimitingdemo.exception.TenantNotFoundException;
import com.shareknowledge.ratelimitingdemo.model.JokeResponse;
import com.shareknowledge.ratelimitingdemo.model.TenantRequest;
import com.shareknowledge.ratelimitingdemo.model.TenantResponse;
import com.shareknowledge.ratelimitingdemo.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Value("${api.ratelimit}")
    private int apiRateLimit;

    @Value("${api.request.time.window}")
    private int apiRequestTimeWindow;

    @Autowired
    private RestTemplate restTemplate;

    private static final String jokesUrl = "https://v2.jokeapi.dev/joke/Any?type=single";

    @Transactional
    public void saveTenant(TenantRequest request) {
        if (request != null && StringUtils.hasText(request.getTenantName())) {
            Optional<Tenant> existingTenant = tenantRepository.findByName(request.getTenantName().trim().toUpperCase());
            if (existingTenant.isPresent()) {
                throw new RuntimeException("tenant exist with " + request.getTenantName() + " name");
            } else {
                Tenant tenant = new Tenant();
                tenant.setName(request.getTenantName().trim().toUpperCase());
                tenant.setCreatedOn(new Date());
                tenant.setTimeWindowStartedOn(new Date());
                tenant.setTotalRequests(0);
                tenant.setRequestsInTimeWindow(0);
                tenantRepository.save(tenant);
            }
        } else {
            throw new RuntimeException("tenant name should not be empty");
        }
    }

    public TenantResponse getTenant(String tenantName) {
        if (StringUtils.hasText(tenantName)) {
            Optional<Tenant> tenantOptional = tenantRepository.findByName(tenantName.trim().toUpperCase());
            if (!tenantOptional.isPresent()) {
                throw new TenantNotFoundException(tenantName);
            } else {
                Tenant tenant = tenantOptional.get();
                TenantResponse response = new TenantResponse();
                response.setName(tenant.getName());
                response.setCreatedOn(tenant.getCreatedOn());
                response.setTimeWindowStartedOn(tenant.getTimeWindowStartedOn());
                response.setTotalRequests(tenant.getTotalRequests());
                response.setRequestsInTimeWindow(tenant.getRequestsInTimeWindow());
                response.setMaxRequestsInTimeWindow(apiRateLimit);
                response.setTimeWindowInMinutes(apiRequestTimeWindow);
                return response;
            }
        } else {
            throw new RuntimeException("tenant name should not be empty");
        }
    }

    public JokeResponse getJoke() throws URISyntaxException {
        try {
            ResponseEntity<JokeResponse> responseEntity = restTemplate.getForEntity(new URI(jokesUrl), JokeResponse.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw e;
        }
    }
}

package com.shareknowledge.ratelimitingdemo.controller;

import com.shareknowledge.ratelimitingdemo.model.JokeResponse;
import com.shareknowledge.ratelimitingdemo.model.TenantRequest;
import com.shareknowledge.ratelimitingdemo.model.TenantResponse;
import com.shareknowledge.ratelimitingdemo.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping("/tenants")
    public ResponseEntity<String> createTenant(@RequestBody TenantRequest request) {
        tenantService.saveTenant(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/tenants")
    public ResponseEntity<TenantResponse> getTenant(@RequestParam("tenantName") String tenantName) {
        TenantResponse response = tenantService.getTenant(tenantName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/joke")
    public ResponseEntity<JokeResponse> getJoke(@RequestParam("tenantName") String tenantName) throws URISyntaxException {
        JokeResponse response = tenantService.getJoke();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

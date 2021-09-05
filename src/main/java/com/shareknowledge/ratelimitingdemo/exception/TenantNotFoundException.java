package com.shareknowledge.ratelimitingdemo.exception;

public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException(String name) {
        super(String.format("Tenant with name %s not found", name));
    }
}

package com.shareknowledge.ratelimitingdemo.model;

import java.util.Date;

public class TenantResponse {

    private String name;

    private Date createdOn;

    private Date timeWindowStartedOn;

    private int totalRequests;

    private int requestsInTimeWindow;

    private int maxRequestsInTimeWindow;

    private int timeWindowInMinutes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getTimeWindowStartedOn() {
        return timeWindowStartedOn;
    }

    public void setTimeWindowStartedOn(Date timeWindowStartedOn) {
        this.timeWindowStartedOn = timeWindowStartedOn;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public int getRequestsInTimeWindow() {
        return requestsInTimeWindow;
    }

    public void setRequestsInTimeWindow(int requestsInTimeWindow) {
        this.requestsInTimeWindow = requestsInTimeWindow;
    }

    public int getMaxRequestsInTimeWindow() {
        return maxRequestsInTimeWindow;
    }

    public void setMaxRequestsInTimeWindow(int maxRequestsInTimeWindow) {
        this.maxRequestsInTimeWindow = maxRequestsInTimeWindow;
    }

    public int getTimeWindowInMinutes() {
        return timeWindowInMinutes;
    }

    public void setTimeWindowInMinutes(int timeWindowInMinutes) {
        this.timeWindowInMinutes = timeWindowInMinutes;
    }
}

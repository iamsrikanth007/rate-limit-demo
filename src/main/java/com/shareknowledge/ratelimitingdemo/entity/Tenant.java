package com.shareknowledge.ratelimitingdemo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeWindowStartedOn;

    private int totalRequests;

    private int requestsInTimeWindow;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}

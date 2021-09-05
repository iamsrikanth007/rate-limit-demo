package com.shareknowledge.ratelimitingdemo.exception;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

public class ErrorResponse {
    private String message;
    private List<String> details;
    private Date timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

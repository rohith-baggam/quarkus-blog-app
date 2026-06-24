package com.learning;

public class HealthReturnResponse {
    public String status;
    public String service;
    public String timestamp;

    public HealthReturnResponse(
            String status,
            String service,
            String timestamp) {
        this.status = status;
        this.service = service;
        this.timestamp = timestamp;

    }

}

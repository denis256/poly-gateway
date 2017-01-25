package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.request.RequestWithVersion;

/**
 * Serialized request, passed to appropriate service
 */
public class ServiceRequest<T extends RequestPayload> extends RequestWithVersion<String, T> {

    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}

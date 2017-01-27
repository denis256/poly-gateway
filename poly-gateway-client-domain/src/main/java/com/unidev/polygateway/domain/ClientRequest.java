package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.request.RequestWithVersion;

/**
 * Serialized request, passed to appropriate service
 */
public class ClientRequest<T extends ClientRequestPayload> extends RequestWithVersion<String, T> {

    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "service='" + service + '\'' +
                "version='" + getVersion() + '\'' +
                "payload='" + getPayload() + '\'' +
                '}';
    }
}

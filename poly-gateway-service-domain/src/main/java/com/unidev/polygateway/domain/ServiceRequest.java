package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.request.RequestWithVersion;

import java.util.HashMap;

/**
 * Serialized request, passed to appropriate service
 * Payload - payload obtained from client
 * Version - gateway version
 * ClientVersion - version field from client
 */
public class ServiceRequest<T extends ClientRequestPayload> extends RequestWithVersion<String, T> {

    private String clientVersion;

    private HashMap<String, String[]> headers;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public HashMap<String, String[]> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String[]> headers) {
        this.headers = headers;
    }
}

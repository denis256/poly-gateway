package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.request.RequestWithVersion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serialized request, passed to appropriate service
 * Payload - payload obtained from client
 * Version - gateway version
 * ClientVersion - version field from client
 */
public class ServiceRequest<T extends ClientRequestPayload> extends RequestWithVersion<String, T> {

    private String clientVersion;

    private List<Map.Entry<String, Object>> headers;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public List<Map.Entry<String, Object>> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Map.Entry<String, Object>> headers) {
        this.headers = headers;
    }
}

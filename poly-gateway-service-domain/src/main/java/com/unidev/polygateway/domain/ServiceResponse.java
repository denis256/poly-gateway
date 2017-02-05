package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.response.ResponseWithVersion;

/**
 * Response from service
 */
public class ServiceResponse<T extends ClientResponsePayload> extends ResponseWithVersion<String, ResponseStatus, T>  {


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ServiceResponse{");
        sb.append("status=").append(status);
        sb.append(", version=").append(version);
        sb.append(", payload=").append(payload);
        sb.append('}');
        return sb.toString();
    }


}

package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.response.ResponseWithVersion;

/**
 * Response from service
 */
public class ClientResponse<T extends ClientResponsePayload> extends ResponseWithVersion<String, ResponseStatus, T>  {

    public static ClientResponse clientResponse() {
        return new ClientResponse();
    }

}

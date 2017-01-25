package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.response.ResponseWithVersion;

/**
 * Response from service
 */
public class ClientResponse extends ResponseWithVersion<String, ClientResponse.Status, ClientResponsePayload>  {

    public enum Status { OK, ERROR}

}

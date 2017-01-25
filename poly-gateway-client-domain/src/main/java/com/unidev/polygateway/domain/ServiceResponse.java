package com.unidev.polygateway.domain;


import com.unidev.platform.common.dto.response.ResponseWithVersion;

/**
 * Response from service
 */
public class ServiceResponse extends ResponseWithVersion<String, ServiceResponse.Status, ClientResponsePayload>  {

    public enum Status { OK, ERROR}

}

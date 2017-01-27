package com.unidev.polygateway;


import com.unidev.polygateway.domain.ClientResponsePayload;
import com.unidev.polygateway.domain.ResponseStatus;
import com.unidev.polygateway.domain.ServiceResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErorrHandler {

    @ExceptionHandler
    public ServiceResponse<ClientResponsePayload> fallback() {
        ServiceResponse<ClientResponsePayload> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(ResponseStatus.ERROR);
        serviceResponse.setVersion(GatewayController.GATEWAY_VERSION);
        return serviceResponse;
    }


}
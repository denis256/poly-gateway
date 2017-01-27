package com.unidev.polygateway;


import com.unidev.polygateway.domain.ClientResponsePayload;
import com.unidev.polygateway.domain.ResponseStatus;
import com.unidev.polygateway.domain.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ErorrHandler extends DefaultErrorAttributes {

    private static Logger LOG = LoggerFactory.getLogger(ErorrHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServiceResponse<ClientResponsePayload> fallback(HttpServletRequest request, HttpServletResponse response,  Throwable e) {
        LOG.error("Handled exception {}", request.getRequestURI(), e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ServiceResponse<ClientResponsePayload> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(ResponseStatus.ERROR);
        serviceResponse.setVersion(GatewayController.GATEWAY_VERSION);
        return serviceResponse;
    }

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Throwable throwable = getError(requestAttributes);
        Throwable cause = throwable.getCause();
        LOG.error("Handled exception", throwable, cause);
        return Collections.emptyMap();
    }


}
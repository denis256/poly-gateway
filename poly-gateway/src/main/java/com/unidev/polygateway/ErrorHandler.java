package com.unidev.polygateway;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler extends ZuulFilter {

    private static Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {

        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletResponse response = ctx.getResponse();
        try {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Service error");
        } catch (final IOException e) {
            LOG.warn("Failed to process request {}", ctx.getRequest(), e);
        }

        return null;
    }


}

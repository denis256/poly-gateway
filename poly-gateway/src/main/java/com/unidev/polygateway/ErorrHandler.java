package com.unidev.polygateway;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErorrHandler extends DefaultErrorAttributes {

    private static Logger LOG = LoggerFactory.getLogger(ErorrHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String fallback(HttpServletRequest request, HttpServletResponse response,  Throwable e) {
        LOG.error("Handled exception {}", request.getRequestURI(), e);
        return "";
    }



}

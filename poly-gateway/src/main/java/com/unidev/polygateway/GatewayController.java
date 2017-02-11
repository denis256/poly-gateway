package com.unidev.polygateway;

import com.unidev.platform.j2ee.common.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Gateway controller service
 */
@RestController
class GatewayController {

	public static final String GATEWAY_VERSION = "0.1.0";

	private static Logger LOG = LoggerFactory.getLogger(GatewayController.class);

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebUtils webUtils;


}



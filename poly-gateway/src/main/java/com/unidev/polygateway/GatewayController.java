package com.unidev.polygateway;

import com.netflix.governator.annotations.binding.Option;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.unidev.platform.j2ee.common.WebUtils;
import com.unidev.polygateway.data.ServiceMapping;
import com.unidev.polygateway.domain.*;
import com.unidev.polygateway.service.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	@Autowired
	private ServiceMapper serviceMapper;

	//@HystrixCommand(fallbackMethod = "fallback")
	@PostMapping("/gateway")
	public @ResponseBody ClientResponse<ClientResponsePayload> handle(@RequestBody ClientRequest<ClientRequestPayload> clientRequest) {
		LOG.info("Client request {}", clientRequest);

		Optional<ServiceMapping> serviceMapping = serviceMapper.match(clientRequest.getService());
		if (!serviceMapping.isPresent()) {
			LOG.warn("Service mapping not found for {}", clientRequest);
			ClientResponse clientResponse = ClientResponse.clientResponse();
			clientResponse.setStatus(ResponseStatus.ERROR);
			clientResponse.setVersion(GatewayController.GATEWAY_VERSION);
			return clientResponse;
		}

		if (clientRequest.getPayload() == null) {
			clientRequest.setPayload(new ClientRequestPayload());
		}

		LOG.info("Resolved service {} to {}", clientRequest, serviceMapping.get());

		ServiceRequest<ClientRequestPayload> serviceRequest = new ServiceRequest<>();
		serviceRequest.setPayload(clientRequest.getPayload());
		serviceRequest.setClientVersion(clientRequest.getVersion());
		List<Map.Entry<String, Object>> headers = webUtils.listAllHeaders(httpServletRequest);
		serviceRequest.setHeaders(headers);

        HttpHeaders serviceRequestHeaders = new HttpHeaders();
        serviceRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HashMap> requestHttpEntity = new HttpEntity<>(new HashMap<>(), serviceRequestHeaders);

		String serviceName = serviceMapping.get().getServiceName();
		serviceName = serviceName.toLowerCase();

		ResponseEntity<ServiceResponse> serviceResponseEntity = restTemplate
					.exchange("http://"+serviceName+"/", HttpMethod.POST, requestHttpEntity, ServiceResponse.class);

        if (serviceResponseEntity.getStatusCode() == HttpStatus.OK) {
            ServiceResponse<ClientResponsePayload> serviceResponseEntityBody = serviceResponseEntity.getBody();
            ClientResponse<ClientResponsePayload> clientResponse = new ClientResponse<>();
            clientResponse.setStatus(serviceResponseEntityBody.getStatus());
            clientResponse.setVersion(GatewayController.GATEWAY_VERSION);
            clientResponse.setPayload(serviceResponseEntityBody.getPayload());

            return clientResponse;
        }

        LOG.warn("Failed to process {} {} {}", clientRequest, serviceMapping.get(), serviceResponseEntity.getStatusCode());
        ClientResponse<ClientResponsePayload> clientResponse = new ClientResponse<>();
        clientResponse.setStatus(ResponseStatus.ERROR);
        clientResponse.setVersion(GatewayController.GATEWAY_VERSION);
        clientResponse.setPayload(null);

		return clientResponse;
	}

	public @ResponseBody  ClientResponse<ClientResponsePayload> fallback(@RequestBody ClientRequest<ClientRequestPayload> clientRequest) {
		LOG.info("Handling fallback method for {}", clientRequest);
		ClientResponse<ClientResponsePayload> serviceResponse = new ClientResponse<>();
		serviceResponse.setStatus(ResponseStatus.ERROR);
		serviceResponse.setVersion(GatewayController.GATEWAY_VERSION);
		return serviceResponse;
	}

}



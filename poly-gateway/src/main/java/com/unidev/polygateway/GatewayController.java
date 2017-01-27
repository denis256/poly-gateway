package com.unidev.polygateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.unidev.platform.j2ee.common.WebUtils;
import com.unidev.polygateway.data.ServiceMapping;
import com.unidev.polygateway.domain.*;
import com.unidev.polygateway.service.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
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

	@HystrixCommand(fallbackMethod = "fallback")
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

		ServiceRequest<ClientRequestPayload> serviceRequest = new ServiceRequest<>();
		serviceRequest.setPayload(clientRequest.getPayload());
		serviceRequest.setClientVersion(clientRequest.getVersion());
		List<Map.Entry<String, Object>> headers = webUtils.listAllHeaders(httpServletRequest);
		serviceRequest.setHeaders(headers);


		ClientResponse<ClientResponsePayload> serviceResponse = new ClientResponse<>();
		serviceResponse.setStatus(ResponseStatus.OK);
		serviceResponse.setVersion(GatewayController.GATEWAY_VERSION);
		return serviceResponse;

//		String uri = httpServletRequest.getRequestURI();
//		LOG.debug("Processing request {}", uri);
//		ServiceMapping serviceMapping = serviceMapper.match(uri);
//		if (serviceMapping == null) {
//			LOG.warn("No mapping found for {}", uri);
//			return ;
//		}
//
//		try {
//
//			ServiceRequest serviceRequest = new ServiceRequest();
//			serviceRequest.setRequestURI(httpServletRequest.getRequestURI());
//
//			HashMap<String, String[]> parameterMap = new HashMap<>(httpServletRequest.getParameterMap());
//			serviceRequest.setParameterMap(parameterMap);
//
//			String method = httpServletRequest.getMethod();
//			String domain = webUtils.getDomain(httpServletRequest);
//			String clientIp = webUtils.getClientIp(httpServletRequest);
//			String referer = webUtils.getReferer(httpServletRequest);
//			String userAgent = webUtils.getUserAgent(httpServletRequest);
//
//			serviceRequest.setMethod(method);
//			serviceRequest.setDomain(domain);
//			serviceRequest.setClientIp(clientIp);
//			serviceRequest.setReferer(referer);
//			serviceRequest.setUserAgent(userAgent);
//
//			Pattern pattern = Pattern.compile(serviceMapping.getUrlPattern());
//			Matcher matcher = pattern.matcher(uri);
//			if (matcher.find()) {
//				int count = matcher.groupCount();
//				for(int id = 0;id<count;id++) {
//					String group = matcher.group(id + 1);
//					serviceRequest.addMatchedUri(group);
//				}
//			}
//
//			List<Map.Entry<String, Object>> list = webUtils.listAllHeaders(httpServletRequest);
//
//			ArrayList<HeaderEntry> entries = new ArrayList<>();
//
//			for(Map.Entry<String, Object> item : list) {
//				entries.add(new HeaderEntry(item.getKey() + "", item.getValue() + ""));
//			}
//
//			serviceRequest.setHeaders(entries);
//
//			String body = IOUtils.toString(httpServletRequest.getInputStream());
//			serviceRequest.setPayload(body);
//
//			// copy custom parameters
//			serviceRequest.setCustomData(serviceMapping.getCustomParameters());
//			serviceRequest.setRequestUriPattern(serviceMapping.getUrlPattern());
//
//			LOG.info("Service request {}", serviceRequest);
//
//			String serviceUri = "http://" + serviceMapping.getServiceName() + "/service";
//			LOG.info("service request for {}", serviceUri);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<ServiceRequest> requestHttpEntity = new HttpEntity<>(serviceRequest,headers);
//
//			ResponseEntity<ServiceResponse> serviceEntity = restTemplate
//					.exchange(serviceUri, HttpMethod.POST, requestHttpEntity, ServiceResponse.class);
//
//			ServiceResponse responseBody = serviceEntity.getBody();
//			LOG.info("service response {}", responseBody);
//
//			if (responseBody.getStatus() == ServiceResponse.Status.ERROR) {
//				LOG.error("Error status for {} {}", httpServletRequest.getRequestURI(), responseBody);
//				return ;
//			}
//
//			if (!Strings.isBlank(responseBody.getContentType())) {
//				httpServletResponse.setContentType(responseBody.getContentType());
//			}
//
//			switch (responseBody.getContent()) {
//
//				case CONTENT_INSIDE:
//					IOUtils.write(responseBody.getPayload().toString(), httpServletResponse.getWriter());
//					break;
//				case LOCAL_FILE:
//					FileInputStream fileInputStream = new FileInputStream(responseBody.getPayload().toString());
//					IOUtils.copy(fileInputStream, httpServletResponse.getWriter());
//					fileInputStream.close();
//					break;
//				case REMOTE_FILE:
//					ResponseEntity<byte[]> entity = restTemplate.getForEntity(responseBody.getPayload().toString(), byte[].class);
//					IOUtils.write(entity.getBody(), httpServletResponse.getWriter());
//					break;
//			}
//
//		}catch (Throwable t) {
//			LOG.error("Failed to process request {}", httpServletRequest.getRequestURI(), t);
//		}
//
//		return ;
//		return null;
	}

	public @ResponseBody  ClientResponse<ClientResponsePayload> fallback(@RequestBody ClientRequest<ClientRequestPayload> clientRequest) {
		LOG.info("Handling fallback method");
		ClientResponse<ClientResponsePayload> serviceResponse = new ClientResponse<>();
		serviceResponse.setStatus(ResponseStatus.ERROR);
		serviceResponse.setVersion(GatewayController.GATEWAY_VERSION);
		return serviceResponse;
	}

}



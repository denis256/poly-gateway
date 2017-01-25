package com.unidev.polygateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unidev.platform.j2ee.common.WebUtils;
import com.unidev.polygateway.domain.ServiceResponse;
import com.unidev.polygateway.service.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Gateway controller service
 */
@RestController
class GatewayController {

	private static Logger LOG = LoggerFactory.getLogger(GatewayController.class);


	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebUtils webUtils;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ServiceMapper serviceMapper;

	//@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping("/service")
	public void handle(HttpServletResponse httpServletResponse) {

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
	}

	public void fallback(HttpServletResponse httpServletResponse) {
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setStatus(ServiceResponse.Status.ERROR);
		try {
			objectMapper.writeValue(httpServletResponse.getOutputStream(), serviceResponse);
		} catch (IOException e) {
			LOG.error("Failed to generate fallback response",e );
		}
	}

}

@ControllerAdvice
class ErorrHandler {

	@Autowired
	private ObjectMapper objectMapper;

	private static Logger LOG = LoggerFactory.getLogger(ErorrHandler.class);

	@ExceptionHandler(value = Exception.class)
	public void fallback(HttpServletResponse httpServletResponse) {
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setStatus(ServiceResponse.Status.ERROR);
		try {
			objectMapper.writeValue(httpServletResponse.getOutputStream(), serviceResponse);
		} catch (IOException e) {
			LOG.error("Failed to generate fallback response",e );
		}
	}


}

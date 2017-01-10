package com.unidev.polygateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Gateway controller service
 */
@RestController
class GatewayController {

	@Autowired
	private RestTemplate restTemplate;

	public String fallback() {
		return "Service not available";
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/list")
	public String list() {
		System.out.println("Loading list");
		//restTemplate.exchange("http://TOMATO-SERVICE/tomatoes", HttpMethod.GET, null,   );
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://tomato-service/tomatoes", String.class);
		System.out.println("Response: " + responseEntity.getStatusCode());
		return responseEntity.getBody() + "";
	}

}

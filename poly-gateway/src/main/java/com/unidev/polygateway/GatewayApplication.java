package com.unidev.polygateway;

import com.unidev.platform.j2ee.common.WebUtils;
import org.jminix.console.servlet.MiniConsoleServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableMongoRepositories
@EnableZuulProxy
public class GatewayApplication implements ServletContextInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public WebUtils webUtils() {
		return new WebUtils();
	}

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addServlet("JmxMiniConsoleServlet", MiniConsoleServlet.class).addMapping("/jmx/*");
    }

}


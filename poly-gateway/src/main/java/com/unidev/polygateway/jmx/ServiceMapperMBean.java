package com.unidev.polygateway.jmx;

import com.unidev.polygateway.data.ServiceMapping;
import com.unidev.polygateway.service.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Basic JMX bean for updating service mappings
 */
@Component
@ManagedResource(objectName = "services:name=ServiceMappingServiceMBean", description = "Service mapping service")
public class ServiceMapperMBean {

    private static Logger LOG = LoggerFactory.getLogger(ServiceMapperMBean.class);

    @Autowired
    private ServiceMapper serviceMapper;

    @ManagedOperation(description = "List services mappings")
    public String list() {
        StringBuilder stringBuilder = new StringBuilder();

        List<ServiceMapping> list = serviceMapper.list();
        for(ServiceMapping serviceMapping : list) {
            stringBuilder.append(serviceMapping.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

    @ManagedOperation(description = "Add mapping")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "id", description = "Id of mapping"),
            @ManagedOperationParameter(name = "urlPattern", description = "Url pattern to match"),
            @ManagedOperationParameter(name = "serviceName", description = "Service name which should process request")
    })
    public String add(String id,
                      String urlPattern,
                      String serviceName) {

        LOG.info("Adding {} {} {}", id, urlPattern, serviceName);

        StringBuilder stringBuilder = new StringBuilder();

        ServiceMapping serviceMapping = new ServiceMapping();
        serviceMapping.setId(id);
        serviceMapping.setUrlPattern(urlPattern);
        serviceMapping.setServiceName(serviceName);

        LOG.info("serviceMapping {}", serviceMapping);

        serviceMapping = serviceMapper.add(serviceMapping);

        LOG.info("serviceMapping2 {}", serviceMapping);

        stringBuilder.append(serviceMapping.toString());

        return stringBuilder.toString();
    }

    @ManagedOperation(description = "Update mapping")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "id", description = "Id of mapping"),
            @ManagedOperationParameter(name = "urlPattern", description = "Url pattern to match"),
            @ManagedOperationParameter(name = "serviceName", description = "Service name which should process request")
    })
    public String update(String id,
                         String urlPattern,
                         String serviceName) {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            ServiceMapping serviceMapping = new ServiceMapping();
            serviceMapping.setId(id);
            serviceMapping.setUrlPattern(urlPattern);
            serviceMapping.setServiceName(serviceName);

            serviceMapping = serviceMapper.add(serviceMapping);
            stringBuilder.append(serviceMapping.toString());

            return stringBuilder.toString();
        }catch (Throwable t) {
            t.printStackTrace();
            return "Error";
        }
    }

    @ManagedOperation(description = "Remove mapping")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "id", description = "Id of mapping")
    })
    public String remove(String id) {
        StringBuilder stringBuilder = new StringBuilder();

        serviceMapper.remove(id);
        stringBuilder.append("Removed: " + id);

        return stringBuilder.toString();
    }

    @ManagedOperation(description = "Match request url")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "url", description = "url to match: /droidads/.*")
    })
    public String match(String url) {
        StringBuilder stringBuilder = new StringBuilder();

        ServiceMapping match = serviceMapper.match(url);
        stringBuilder.append("Match for url: " + url + "\n");
        stringBuilder.append(match + "\n");

        return stringBuilder.toString();
    }
}

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
import java.util.Optional;

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
            @ManagedOperationParameter(name = "externalName", description = "external service name"),
            @ManagedOperationParameter(name = "internalName", description = "internal service name")
    })
    public String add(String externalName,
                      String internalName) {

        LOG.info("Adding {} {}", externalName, internalName);

        StringBuilder stringBuilder = new StringBuilder();

        ServiceMapping serviceMapping = new ServiceMapping();
        serviceMapping.setId(externalName);
        serviceMapping.setServiceName(internalName);

        LOG.info("serviceMapping {}", serviceMapping);

        serviceMapping = serviceMapper.add(serviceMapping);

        LOG.info("serviceMapping2 {}", serviceMapping);

        stringBuilder.append(serviceMapping.toString());

        return stringBuilder.toString();
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

    @ManagedOperation(description = "Match service")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "externalName", description = "External service name used to match")
    })
    public String match(String externalName) {
        StringBuilder stringBuilder = new StringBuilder();

        Optional<ServiceMapping> serviceMapping = serviceMapper.match(externalName);
        if (serviceMapping.isPresent()) {
            stringBuilder.append("Match for url: " + externalName + "\n");
            stringBuilder.append(serviceMapping + "\n");
        } else {
            stringBuilder.append("Service mapping not found\n");

        }

        return stringBuilder.toString();
    }
}

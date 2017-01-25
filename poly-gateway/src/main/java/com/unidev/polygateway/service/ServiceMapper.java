package com.unidev.polygateway.service;


import com.unidev.polygateway.data.ServiceMapping;
import com.unidev.polygateway.data.ServiceMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service which identify service by url pattern
 */
@Service
public class ServiceMapper {

    private static Logger LOG = LoggerFactory.getLogger(ServiceMapping.class);

    @Autowired
    private ServiceMappingRepository serviceMappingRepository;

    /**
     * List all services
     * @return
     */
    public List<ServiceMapping> list() {
        return serviceMappingRepository.findAll();
    }

    /**
     * Add/Update entity
     * @param serviceMapping
     * @return
     */
    public ServiceMapping add(ServiceMapping serviceMapping) {
        return serviceMappingRepository.save(serviceMapping);
    }

    /**
     * Remove entity by id
     * @param id
     */
    public void remove(String id) {
        serviceMappingRepository.delete(id);
    }

    /**
     * Fetch service mapping by external name
     * @return
     */
    public Optional<ServiceMapping> match(String externalServiceName) {
        ServiceMapping serviceMapping = serviceMappingRepository.findOne(externalServiceName);
        if (serviceMapping != null) {
            return Optional.of(serviceMapping);
        }
        return Optional.empty();
    }

}

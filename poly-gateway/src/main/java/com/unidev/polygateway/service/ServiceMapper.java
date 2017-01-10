package com.unidev.polygateway.service;


import com.unidev.polygateway.data.ServiceMapping;
import com.unidev.polygateway.data.ServiceMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * Check if url matching pattern
     * @param url
     * @return
     */
    public ServiceMapping match(String url) {
        List<ServiceMapping> list = list();
        for(ServiceMapping serviceMapping : list) {
            try {
                if (url.matches(serviceMapping.getUrlPattern())) { //TODO: use db pattern matching
                    return serviceMapping;
                }
            }catch (Throwable t) {
                LOG.warn("Error processing  {}", serviceMapping, t);
            }
        }
        LOG.warn("No matching service for {}", url);
        return null;
    }

}

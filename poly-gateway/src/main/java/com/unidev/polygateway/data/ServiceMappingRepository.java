package com.unidev.polygateway.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Service mapping repository
 */
@Repository
public interface ServiceMappingRepository extends MongoRepository<ServiceMapping, String> {
}

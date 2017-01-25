package com.unidev.polygateway.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity for mapping  external services to internal names
 */
@Document(collection = "services_mapping")
public class ServiceMapping {

    @Id
    private String id;

    @Indexed
    private String serviceName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "ServiceMapping{" +
                "id='" + id + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}

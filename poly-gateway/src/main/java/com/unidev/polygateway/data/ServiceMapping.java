package com.unidev.polygateway.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

/**
 * Entity for mapping url patterns to service
 */
@Document(collection = "services")
public class ServiceMapping {

    @Id
    private String id;

    private String urlPattern;

    private String serviceName;

    private HashMap<String, Object> customParameters = new HashMap<>();

    public HashMap<String, Object> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(HashMap<String, Object> customParameters) {
        this.customParameters = customParameters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceMapping that = (ServiceMapping) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (urlPattern != null ? !urlPattern.equals(that.urlPattern) : that.urlPattern != null) return false;
        return !(serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (urlPattern != null ? urlPattern.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServiceMapping{" +
                "id='" + id + '\'' +
                ", urlPattern='" + urlPattern + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}

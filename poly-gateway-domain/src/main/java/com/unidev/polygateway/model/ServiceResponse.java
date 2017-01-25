package com.unidev.polygateway.model;


import com.unidev.platform.common.dto.response.Response;

import java.util.HashMap;

/**
 * Response from service
 */
public class ServiceResponse extends Response {

    public enum Status { OK, ERROR}
    public enum ContentLocation { CONTENT_INSIDE, LOCAL_FILE, REMOTE_FILE }


    private Status status = Status.OK;
    private ContentLocation content = ContentLocation.CONTENT_INSIDE;

    private String contentType;

    private HashMap<String, Object> customData = new HashMap<>();


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ContentLocation getContent() {
        return content;
    }

    public void setContent(ContentLocation content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public HashMap<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(HashMap<String, Object> customData) {
        this.customData = customData;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "status=" + status +
                ", content=" + content +
                ", contentType='" + contentType + '\'' +
                ", payload='" + payload + '\'' +
                ", customData=" + customData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceResponse that = (ServiceResponse) o;

        if (status != that.status) return false;
        if (content != that.content) return false;
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;
        return !(customData != null ? !customData.equals(that.customData) : that.customData != null);

    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (customData != null ? customData.hashCode() : 0);
        return result;
    }
}

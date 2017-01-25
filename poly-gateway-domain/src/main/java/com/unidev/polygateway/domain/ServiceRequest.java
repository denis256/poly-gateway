package com.unidev.polygateway.domain;



import com.unidev.platform.common.dto.request.Request;
import com.unidev.polygateway.model.HeaderEntry;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Serialized request, passed to appropriate service
 * <pre>
 * </pre>
 */
public class ServiceRequest extends Request {

    private HashMap<String, String[]> parameterMap = new HashMap<>();
    private String method;
    private String requestURI;
    private ArrayList<HeaderEntry> headers;
    private String requestUriPattern;
    private ArrayList<String> matchedUri = new ArrayList<>();

    private String domain;
    private String clientIp;
    private String referer;
    private String userAgent;

    private HashMap<String, Object> customData = new HashMap<>();

    public HeaderEntry fetchHeader(String key) {
        for(HeaderEntry entry : headers) {
            if (key.equalsIgnoreCase(entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    public String getRequestUriPattern() {
        return requestUriPattern;
    }

    public void addMatchedUri(String match) {
        matchedUri.add(match);
    }

    public ArrayList<String> getMatchedUri() {
        return matchedUri;
    }

    public void setMatchedUri(ArrayList<String> matchedUri) {
        this.matchedUri = matchedUri;
    }

    public void setRequestUriPattern(String requestUriPattern) {
        this.requestUriPattern = requestUriPattern;
    }

    public HashMap<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(HashMap<String, Object> customData) {
        this.customData = customData;
    }

    public HashMap<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(HashMap<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public ArrayList<HeaderEntry> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<HeaderEntry> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    @Override
    public String toString() {
        return "ServiceRequest{" +
                "parameterMap=" + parameterMap +
                ", method='" + method + '\'' +
                ", requestURI='" + requestURI + '\'' +
                ", headers=" + headers +
                ", requestUriPattern='" + requestUriPattern + '\'' +
                ", matchedUri=" + matchedUri +
                ", domain='" + domain + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", referer='" + referer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", customData=" + customData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceRequest that = (ServiceRequest) o;

        if (parameterMap != null ? !parameterMap.equals(that.parameterMap) : that.parameterMap != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (requestURI != null ? !requestURI.equals(that.requestURI) : that.requestURI != null) return false;
        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;
        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;
        if (clientIp != null ? !clientIp.equals(that.clientIp) : that.clientIp != null) return false;
        if (referer != null ? !referer.equals(that.referer) : that.referer != null) return false;
        if (userAgent != null ? !userAgent.equals(that.userAgent) : that.userAgent != null) return false;
        return !(customData != null ? !customData.equals(that.customData) : that.customData != null);

    }

    @Override
    public int hashCode() {
        int result = parameterMap != null ? parameterMap.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (requestURI != null ? requestURI.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (clientIp != null ? clientIp.hashCode() : 0);
        result = 31 * result + (referer != null ? referer.hashCode() : 0);
        result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
        result = 31 * result + (customData != null ? customData.hashCode() : 0);
        return result;
    }
}

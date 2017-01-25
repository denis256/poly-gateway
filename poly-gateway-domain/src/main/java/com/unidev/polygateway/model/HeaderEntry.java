package com.unidev.polygateway.model;

/**
 * Header entry mappting value
 */
public class HeaderEntry {

    private String key;
    private String value;

    public HeaderEntry() {
    }

    public HeaderEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HeaderEntry{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }


}

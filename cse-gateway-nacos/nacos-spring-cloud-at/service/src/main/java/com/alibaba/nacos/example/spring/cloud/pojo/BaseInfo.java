package com.alibaba.nacos.example.spring.cloud.pojo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

public class BaseInfo {

    private static final BaseInfo BASE_INFO = new BaseInfo();

    private BaseInfo() {
    }

    public static BaseInfo getInstance() {
        return BASE_INFO;
    }

    @Value("${test1.config:testNacos-1}")
    private String testNacos;

    @Value("${service_meta_version:${SERVICE_META_VERSION:${service.meta.version:1.0.1}}}")
    private String version;

    private int port;

    private String conditions;

    private Map<String, String> valueMap = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    public String getTestNacos() {
        return testNacos;
    }

    public BaseInfo setTestNacos(String testNacos) {
        this.testNacos = testNacos;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public BaseInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public int getPort() {
        return port;
    }

    public BaseInfo setPort(int port) {
        this.port = port;
        return this;
    }

    public Map<String, String> getValueMap() {
        return valueMap;
    }

    public BaseInfo setValueMap(Map<String, String> valueMap) {
        this.valueMap = valueMap;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public BaseInfo setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getConditions() {
        return conditions;
    }

    public BaseInfo setConditions(String conditions) {
        this.conditions = conditions;
        return this;
    }
}

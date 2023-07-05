package com.alibaba.nacos.example.spring.cloud.controller;

import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.example.spring.cloud.pojo.BaseInfo;

@RestController
@RequestMapping("/service")
@RefreshScope
public class TestServiceController {

    @Value("${test1.config:testNacos-1}")
    private String testNacos;

    @Value("${service_meta_version:${SERVICE_META_VERSION:${service.meta.version:1.0.1}}}")
    private String version;

    @Value("${server.port}")
    private int port;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseInfo get(HttpServletRequest request, @RequestParam(required = false) String conditions) {
        dealHeader(request);

        BaseInfo.getInstance().setConditions(conditions);
        return BaseInfo.getInstance();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseInfo post(HttpServletRequest request, @RequestBody BaseInfo baseInfo) {
        dealHeader(request);

        BaseInfo.getInstance().getValueMap().putAll(baseInfo.getValueMap());
        return BaseInfo.getInstance();
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public BaseInfo patch(HttpServletRequest request, @RequestBody BaseInfo baseInfo) {
        dealHeader(request);

        baseInfo.getValueMap().forEach((k, v) -> {
            if (Objects.nonNull(BaseInfo.getInstance().getValueMap().get(k))) {
                BaseInfo.getInstance().getValueMap().put(k, v);
            }
        });

        return BaseInfo.getInstance();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public BaseInfo put(HttpServletRequest request, @RequestBody BaseInfo baseInfo) {
        dealHeader(request);

        baseInfo.getValueMap().clear();
        BaseInfo.getInstance().getValueMap().putAll(baseInfo.getValueMap());

        return BaseInfo.getInstance();
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public BaseInfo delete(HttpServletRequest request, @RequestBody BaseInfo baseInfo) {
        dealHeader(request);

        baseInfo.getValueMap().forEach((k, v) -> {
            if (Objects.nonNull(BaseInfo.getInstance().getValueMap().get(k))) {
                BaseInfo.getInstance().getValueMap().remove(k);
            }
        });

        return BaseInfo.getInstance();
    }

    private void dealHeader(HttpServletRequest request) {
        initIfNull();

        BaseInfo.getInstance().getHeaders().clear();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            BaseInfo.getInstance().getHeaders().put(headerName, request.getHeader(headerName));
        }
    }

    private void initIfNull() {
        if (StringUtils.hasLength(BaseInfo.getInstance().getVersion())) {
            return;
        }
        BaseInfo.getInstance().setPort(port);
        BaseInfo.getInstance().setVersion(version);
        BaseInfo.getInstance().setTestNacos(testNacos);
    }

}
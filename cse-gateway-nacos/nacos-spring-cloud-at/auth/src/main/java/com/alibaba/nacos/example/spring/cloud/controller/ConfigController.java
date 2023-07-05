package com.alibaba.nacos.example.spring.cloud.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("")
public class ConfigController {

    private static final String CHECK_HEADER = "x-ext-authz";
    private static final String ALLOWED_VALUE = "allow";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    @Value("${test1.config:testNacos-1}")
    private String testNacos;

    @Value("${service_meta_version:${SERVICE_META_VERSION:${service.meta.version:1.0.1}}}")
    private String version;

    @Value("${server.port}")
    private int port;

    @RequestMapping(value = "community", method = RequestMethod.GET)
    public String echo(HttpServletRequest request) {
        return "provider port is " + port + ", version is " + version + ", config is " + testNacos
            + ",Header is: \"key: " + request.getHeader("key") + "\".";
    }

    /**
     * 模糊路径匹配auth前面不能加/ 否则变为绝对路径匹配了
     */
    @RequestMapping(value = {"auth**", "auth/**/*"}, method = RequestMethod.GET)
    public ResponseEntity<String> authz(HttpServletRequest request,
        @RequestHeader(value = CHECK_HEADER, required = false) String checkHeader) {
        ResponseEntity<String> responseEntity;

        if (checkHeader == null) {
            checkHeader = "";
        }
        if (checkHeader.equalsIgnoreCase(ALLOWED_VALUE)) {
            LOGGER.info("{}, {}, {}", HttpStatus.OK, checkHeader, request.getRequestURI());
            responseEntity = new ResponseEntity<>("Request Success", HttpStatus.OK);
            return responseEntity;
        }
        LOGGER.info("{}, {}, {}", HttpStatus.FORBIDDEN, checkHeader, request.getRequestURI());
        responseEntity = new ResponseEntity<>("Request Fail", HttpStatus.FORBIDDEN);
        return responseEntity;
    }
}
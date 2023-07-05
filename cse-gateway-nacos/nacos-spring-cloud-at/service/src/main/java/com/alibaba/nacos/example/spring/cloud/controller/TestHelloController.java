package com.alibaba.nacos.example.spring.cloud.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RefreshScope
public class TestHelloController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String get(HttpServletRequest request) {
        return "hello, this is service";
    }

}
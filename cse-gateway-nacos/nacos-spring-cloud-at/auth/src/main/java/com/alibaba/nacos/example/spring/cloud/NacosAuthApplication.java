package com.alibaba.nacos.example.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosAuthApplication.class, args);
	}
}

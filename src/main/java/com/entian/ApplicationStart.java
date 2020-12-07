package com.entian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jianggangli
 * @version 1.0 2020/6/30 16:11
 * 功能:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(value = "com.entian.service.mapper")
public class ApplicationStart {


    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}

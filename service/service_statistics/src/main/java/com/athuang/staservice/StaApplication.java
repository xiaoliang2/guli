package com.athuang.staservice;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-10-04 15:32
 **/

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-10-04 15:32
 **/

@SpringBootApplication
@ComponentScan("com.athuang")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.athuang.staservice.mapper")
@EnableScheduling
public class StaApplication {
    public static void main(String args[])
    {
        SpringApplication.run(StaApplication.class,args);
    }
}

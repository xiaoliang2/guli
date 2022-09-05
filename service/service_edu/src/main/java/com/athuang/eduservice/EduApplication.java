package com.athuang.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: guli_parent
 *
 * @description: 启动类
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-02 15:58
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.athuang"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}

package com.athuang.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-27 18:06
 **/
@SpringBootApplication
@ComponentScan("com.athuang")
@MapperScan("com.athuang.educenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {

        SpringApplication.run(UcenterApplication.class,args);
    }
}

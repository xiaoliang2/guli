package com.athuang.eduservice.config;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-02 16:00
 **/

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-02 16:00
 **/
@Configuration
@MapperScan("com.athuang.eduservice.mapper")
public class Educonfig {
//     逻辑删除
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
//     分页
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
//     @Bean
//     public MybatisPlusInterceptor mybatisPlusInterceptor() {
//         MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//         interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 注意使用哪种数据库
//         return interceptor;
//     }


}

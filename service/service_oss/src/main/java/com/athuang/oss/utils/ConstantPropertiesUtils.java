package com.athuang.oss.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: guli_parent
 *
 * @description: 读取阿里云oss配置类
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-12 17:03
 **/
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class ConstantPropertiesUtils {


    private String endpoint;

    private String keyId;

    private String keySecret;

    private String bucketName;
}

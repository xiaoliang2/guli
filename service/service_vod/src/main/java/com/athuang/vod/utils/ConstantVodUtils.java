package com.athuang.vod.utils;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-20 16:14
 **/

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-20 16:14
 **/
@Component
public class ConstantVodUtils implements InitializingBean {

    public static  String ACCESS_KEY_ID ;
    public static  String ACCESS_KEY_SECRET ;
    @Value("${aliyun.vod.file.keyid}")
    private  String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    @Override
    public void afterPropertiesSet() throws Exception {

        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}

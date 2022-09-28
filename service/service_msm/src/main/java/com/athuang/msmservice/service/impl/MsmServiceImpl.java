package com.athuang.msmservice.service.impl;

import com.athuang.msmservice.service.MsmService;
import com.athuang.msmservice.utils.ZhenZiYunSMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-27 16:40
 **/
@Service

public class MsmServiceImpl implements MsmService {

    @Autowired
    private ZhenZiYunSMSUtils zhenZiYunSMSUtils;

    @Override
    public boolean send(String param,String phone) {

        boolean flag = false;
        if (StringUtils.isEmpty(phone))
        {
            return false;
        }
        try {
             flag = zhenZiYunSMSUtils.sendSMS(param,phone);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return flag;
    }
}

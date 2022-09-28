package com.athuang.msmservice.controller;

import com.athuang.commonutils.R;
import com.athuang.msmservice.service.MsmService;
import com.athuang.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-27 16:39
 **/
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) throws Exception {

//        从redis中获取验证码，如果能获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);

        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
        code = RandomUtil.getFourBitRandom();

//        调用service发送短信方法
        boolean inSend = msmService.send(code,phone);

        if (inSend)
        {
//            发送成功，把发送成功的验证码存入redis；
//            并且设置有效时间
            redisTemplate.opsForValue().set(phone,code,30, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }

    }

}

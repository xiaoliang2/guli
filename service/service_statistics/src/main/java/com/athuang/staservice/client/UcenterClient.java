package com.athuang.staservice.client;

import com.athuang.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-10-04 15:47
 **/
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //     查询某一天注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}

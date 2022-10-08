package com.athuang.eduorder.client;

import com.athuang.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-10-03 16:16
 **/
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //     根据用户id获取用户信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}

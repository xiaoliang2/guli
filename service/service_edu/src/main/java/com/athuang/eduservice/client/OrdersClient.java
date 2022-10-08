package com.athuang.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-10-03 19:32
 **/
@Component
@FeignClient(value = "service-order",fallback = OrdersClientImpl.class )
public interface OrdersClient {
    //     根据课程id和用户id查询订单表中订单状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable(value = "courseId") String courseId, @PathVariable(value = "memberId") String memberId);
}

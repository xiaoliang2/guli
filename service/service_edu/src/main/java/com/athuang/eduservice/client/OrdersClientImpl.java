package com.athuang.eduservice.client;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-10-03 20:11
 **/

import org.springframework.stereotype.Component;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-10-03 20:11
 **/
@Component
public class OrdersClientImpl implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}

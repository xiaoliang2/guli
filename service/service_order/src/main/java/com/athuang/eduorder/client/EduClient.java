package com.athuang.eduorder.client;

import com.athuang.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-10-03 16:15
 **/
@Component
@FeignClient("service-edu")
public interface EduClient {

    //     根据课程id查询课程信息
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}

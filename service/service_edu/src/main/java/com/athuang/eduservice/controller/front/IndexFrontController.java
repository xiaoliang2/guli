package com.athuang.eduservice.controller.front;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-26 16:31
 **/

import com.athuang.commonutils.R;
import com.athuang.eduservice.entity.EduCourse;
import com.athuang.eduservice.entity.EduTeacher;
import com.athuang.eduservice.service.EduCourseService;
import com.athuang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-26 16:31
 **/
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

//     查询前8条热门课程，前四名师

    @GetMapping("index")
    public R index()
    {
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();

        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> eduCourses = courseService.list(wrapperCourse);

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> eduTeachers = teacherService.list(wrapperTeacher);


        return R.ok().data("eduCourses",eduCourses).data("eduTeachers",eduTeachers);

    }
}

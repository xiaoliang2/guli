package com.athuang.eduservice.controller.front;

import com.athuang.commonutils.JwtUtils;
import com.athuang.commonutils.R;
import com.athuang.commonutils.ordervo.CourseWebVoOrder;
import com.athuang.eduservice.client.OrdersClient;
import com.athuang.eduservice.entity.EduCourse;
import com.athuang.eduservice.entity.EduTeacher;
import com.athuang.eduservice.entity.chapter.ChapterVo;
import com.athuang.eduservice.entity.frontvo.CourseFrontVo;
import com.athuang.eduservice.entity.frontvo.CourseWebVo;
import com.athuang.eduservice.service.EduChapterService;
import com.athuang.eduservice.service.EduCourseService;
import com.athuang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-30 18:30
 **/
@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrdersClient ordersClient;

//     条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo)
    {
        Page<EduCourse> pageCourse = new Page<>(page,limit);

        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
//         返回分页所有数据
        return R.ok().data(map);

    }

//     课程详情方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request)
    {
//         根据课程id,编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

//         根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

//         根据课程id和用户id查询用户是否已经支付过
        int code = 20000;
        boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        if (StringUtils.isEmpty(JwtUtils.getMemberIdByJwtToken(request))){
            code = 28004;
        }
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse).code(code);
    }

//     根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id)
    {
//         根据课程id查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);

        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoOrder);

        return courseWebVoOrder;
    }


}

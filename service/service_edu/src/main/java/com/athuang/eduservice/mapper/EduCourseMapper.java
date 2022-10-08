package com.athuang.eduservice.mapper;

import com.athuang.eduservice.entity.EduCourse;
import com.athuang.eduservice.entity.frontvo.CourseWebVo;
import com.athuang.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-09-15
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);


    CourseWebVo getBaseCourseInfo(String courseId);
}

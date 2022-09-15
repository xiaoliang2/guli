package com.athuang.eduservice.service.impl;

import com.athuang.eduservice.entity.EduCourse;
import com.athuang.eduservice.entity.EduCourseDescription;
import com.athuang.eduservice.entity.vo.CourseInfoVo;
import com.athuang.eduservice.mapper.EduCourseMapper;
import com.athuang.eduservice.service.EduCourseDescriptionService;
import com.athuang.eduservice.service.EduCourseService;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-15
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

//    添加课程信息
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

//        向课程表添加课程信息
//        CourseInfoVo对象转换成eduCourse对象
        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        System.out.println(courseInfoVo.getId());
        System.out.println(eduCourse.getSubjectId());

        int insert = baseMapper.insert(eduCourse);

        if (insert <= 0)
        {
            throw new GuliException(20001,"添加课程信息失败");
        }
        String cid = eduCourse.getId();
//        向课程简介添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);


        return cid;
    }
}

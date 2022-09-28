package com.athuang.eduservice.service.impl;

import com.athuang.eduservice.config.Educonfig;
import com.athuang.eduservice.entity.EduCourse;
import com.athuang.eduservice.entity.EduCourseDescription;
import com.athuang.eduservice.entity.vo.CourseInfoVo;
import com.athuang.eduservice.entity.vo.CoursePublishVo;
import com.athuang.eduservice.mapper.EduCourseMapper;
import com.athuang.eduservice.service.EduChapterService;
import com.athuang.eduservice.service.EduCourseDescriptionService;
import com.athuang.eduservice.service.EduCourseService;
import com.athuang.eduservice.service.EduVideoService;
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
//    注入小节
    @Autowired
    private EduVideoService eduVideoService;
//    注入章节
    @Autowired
    private EduChapterService eduChapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

//        向课程表添加课程信息
//        CourseInfoVo对象转换成eduCourse对象
        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoVo,eduCourse);
//        System.out.println(courseInfoVo.getId());
//        System.out.println(eduCourse.getSubjectId());

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

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

//        查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

//        查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription,courseInfoVo);

        return courseInfoVo;
    }



    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
//        修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if (update == 0)
        {
            throw new GuliException(2001,"修改课程信息失败");
        }

//        修改描述表
        EduCourseDescription  description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {

        CoursePublishVo coursePublishVo = baseMapper.getPublishCourseInfo(id);

        return coursePublishVo;
    }

    @Override
    public void removeCourse(String courseId) {

//        根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

//        根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

//        根据课程id删除描述
        courseDescriptionService.removeById(courseId);

//        根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0)
        {
            throw new GuliException(20001,"删除失败");
        }
    }
}

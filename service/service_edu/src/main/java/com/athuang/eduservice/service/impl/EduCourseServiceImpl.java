package com.athuang.eduservice.service.impl;

import com.athuang.eduservice.config.Educonfig;
import com.athuang.eduservice.entity.EduCourse;
import com.athuang.eduservice.entity.EduCourseDescription;
import com.athuang.eduservice.entity.frontvo.CourseFrontVo;
import com.athuang.eduservice.entity.frontvo.CourseWebVo;
import com.athuang.eduservice.entity.vo.CourseInfoVo;
import com.athuang.eduservice.entity.vo.CoursePublishVo;
import com.athuang.eduservice.mapper.EduCourseMapper;
import com.athuang.eduservice.service.EduChapterService;
import com.athuang.eduservice.service.EduCourseDescriptionService;
import com.athuang.eduservice.service.EduCourseService;
import com.athuang.eduservice.service.EduVideoService;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//     添加课程信息
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
//     注入小节
    @Autowired
    private EduVideoService eduVideoService;
//     注入章节
    @Autowired
    private EduChapterService eduChapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

//         向课程表添加课程信息
//         CourseInfoVo对象转换成eduCourse对象
        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoVo,eduCourse);
//         System.out.println(courseInfoVo.getId());
//         System.out.println(eduCourse.getSubjectId());

        int insert = baseMapper.insert(eduCourse);

        if (insert <= 0)
        {
            throw new GuliException(20001,"添加课程信息失败");
        }
        String cid = eduCourse.getId();
//         向课程简介添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);


        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

//         查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

//         查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription,courseInfoVo);

        return courseInfoVo;
    }



    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
//         修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if (update == 0)
        {
            throw new GuliException(2001,"修改课程信息失败");
        }

//         修改描述表
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

//         根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

//         根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

//         根据课程id删除描述
        courseDescriptionService.removeById(courseId);

//         根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0)
        {
            throw new GuliException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {

//         根据讲师id查询讲师课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//         判断条件值是否为空，不为空拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId()))  //  一级分类
        {
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId()))  //  二级分类
        {
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort()))  //  关注度
        {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { // 最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {// 价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();// 下一页
        boolean hasPrevious = pageParam.hasPrevious();// 上一页

        // 把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        // map返回
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {


        return baseMapper.getBaseCourseInfo(courseId);
    }
}

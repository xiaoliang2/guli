package com.athuang.eduservice.entity.vo;

import lombok.Data;

/**
 * @program: guli_parent
 *
 * @description: 最终发布vo实体类
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-18 17:36
 **/

@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;// 只用于显示
}

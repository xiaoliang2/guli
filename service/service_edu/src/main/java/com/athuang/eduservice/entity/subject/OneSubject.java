package com.athuang.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: guli_parent
 *
 * @description: 课程分类一级分类实体类
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-13 19:38
 **/
@Data
public class OneSubject {

    private String id;

    private String title;

//     一个一级分类有多个二级分类

    private List<TwoSubject> children = new ArrayList<>();
}

package com.athuang.eduservice.controller;


import com.athuang.commonutils.R;
import com.athuang.eduservice.entity.subject.OneSubject;
import com.athuang.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-13
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

//     添加课程分类
//     获取上传过来的文件，把文件内容读取出来

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file)
    {
//         上传过来的excel文件

        eduSubjectService.saveSubject(file,eduSubjectService);

        return R.ok();
    }

//     课程分类列表（树形结构）
    @GetMapping("getAllSubject")
    public R getAllSubject()
    {
//         list集合中泛型是一级分类
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}


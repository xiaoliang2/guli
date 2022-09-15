package com.athuang.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.athuang.eduservice.entity.EduSubject;
import com.athuang.eduservice.entity.excel.SubjectData;
import com.athuang.eduservice.service.EduSubjectService;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @program: guli_parent
 *
 * @description: easyexcel读操作监听器
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-13 15:36
 **/

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
//    因为这个类要自己管理，不能交给spring管理，所以不能注入，进行数据库操作

    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

//    读取excel内容，一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {

        if(subjectData == null)
        {
            throw new GuliException(20001,"文件数据为空");
        }

//        一行一行读取，每次读取两个值，第一个值为一级分类，第二个值为二级分类

//        判断一级分类是否为空
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService,subjectData.getOneSubjectName());
//        没有相同一级分类,进行添加
        if (existOneSubject == null)
        {
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());

            eduSubjectService.save(existOneSubject);

        }

//        获取一级分类id值
        String pid = existOneSubject.getId();
//        添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService,subjectData.getTwoSubjectName(),pid);

        if (existTwoSubject == null)
        {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());

            eduSubjectService.save(existTwoSubject);

        }


    }

//    判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name)
    {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);

        return oneSubject;
    }

//    判断二级分类不能重复添加
private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid)
{
    QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
    wrapper.eq("title",name);
    wrapper.eq("parent_id","pid");
    EduSubject twoSubject = eduSubjectService.getOne(wrapper);

    return twoSubject;
}

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

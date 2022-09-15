package com.athuang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.athuang.eduservice.entity.EduSubject;
import com.athuang.eduservice.entity.excel.SubjectData;
import com.athuang.eduservice.entity.subject.OneSubject;
import com.athuang.eduservice.entity.subject.TwoSubject;
import com.athuang.eduservice.listener.SubjectExcelListener;
import com.athuang.eduservice.mapper.EduSubjectMapper;
import com.athuang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {

        try {
//            文件输入流
            InputStream in = file.getInputStream();
//            调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {


//        查询一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
//        查询二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

//        创建list集合，用于存储最终的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
//        封装一级分类
//        查询出来所有的一级分类list集合遍历，得到每一个一级分类对象，获取每一个一级分类对象值
//        封装到要求的list集合里面list<onesubject> finalsubjectlist
//        遍历oneSubjectList集合

        for (int i = 0 ; i < oneSubjectList.size();i ++)
        {
//            得到oneSubjectList每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);

//            把eduSubject里面值获取出来，放到Onesubject对象里面
//            把多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();

//            使用对象复制函数
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

//            在一级分类里面遍历二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
//            遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++)
            {
//                获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(m);
//                判断二级分类是否属于当前一级分类
                if (tSubject.getParentId().equals(eduSubject.getId()))
                {

//                    把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);

                    twoFinalSubjectList.add(twoSubject);
                }
            }
//            把所有一级分类下面的二级分类梵高一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}

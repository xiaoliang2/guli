package com.athuang.eduservice.controller;



import com.athuang.commonutils.R;
import com.athuang.eduservice.entity.EduTeacher;
import com.athuang.eduservice.entity.vo.TeacherQuery;
import com.athuang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-02
 */

@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){

        List<EduTeacher> list = teacherService.list(null);

//        int a = 10/0;

        return R.ok().data("items",list);
    }
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id" , value = "讲师ID", required = true) @PathVariable String id){

        boolean flag =  teacherService.removeById(id);
        if (flag)
        {
            return R.ok();
        }else{
            return R.error();
        }


    }

//    分页查询方法

    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit)
    {

        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        System.out.println(limit);
        System.out.println(current);
//        调用方法实现分页
//        调用方法的时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);
//        总记录数
        long total = pageTeacher.getTotal();

//        数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();
        System.out.println(records);
//        Map map = new HashMap();
//
//        map.put("total",total);
//        map.put("rows",records);
//
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable(required = false) long limit,@RequestBody TeacherQuery teacherQuery)
    {

//        创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
//        构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

//        多条件组合查询，使用动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
//        判断条件是否为空，如果不为空则拼接条件
        if (!StringUtils.isEmpty(name))
        {
//            构建条件
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level))
        {
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin))
        {
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end))
        {
            wrapper.le("gmt_modified",end);
        }

//        排序
        wrapper.orderByDesc("gmt_create");
//        调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

//    添加讲师接口方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher)
    {
        boolean save = teacherService.save(eduTeacher);

        if (save){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

//    根据id查询

    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id)
    {
        EduTeacher eduTeacher = teacherService.getById(id);

        return R.ok().data("teacher",eduTeacher);
    }

//    讲师修改

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher)
    {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }



}


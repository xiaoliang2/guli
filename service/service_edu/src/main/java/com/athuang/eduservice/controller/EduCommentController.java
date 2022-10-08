package com.athuang.eduservice.controller;


import com.athuang.commonutils.JwtUtils;
import com.athuang.commonutils.R;
import com.athuang.eduservice.config.Educonfig;
import com.athuang.eduservice.entity.EduComment;
import com.athuang.eduservice.entity.EduTeacher;
import com.athuang.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-10-02
 */
@RestController
@RequestMapping("/eduservice/educomment")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @PostMapping("pageComment/{id}/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit,
                             @PathVariable String id)
    {

        Page<EduComment> pageComment = new Page<>(current,limit);
//         System.out.println(limit);
//         System.out.println(current);
//         调用方法实现分页
//         调用方法的时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        commentService.page(pageComment,wrapper);
//         总记录数
        long total = pageComment.getTotal();

//         数据list集合
        List<EduComment> records = pageComment.getRecords();
//         System.out.println(records);
//         Map map = new HashMap();
//
//         map.put("total",total);
//         map.put("rows",records);
//
//         return R.ok().data(map);
        return R.ok().data("total",total).data("rows",records);
    }

//     添加评论方法
    @PostMapping("addcomment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request)
    {
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberIdByJwtToken))
        {
            return R.ok().code(28004).message("请登陆后再评论");
        }
        boolean save = commentService.save(eduComment);

        if (save){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

}


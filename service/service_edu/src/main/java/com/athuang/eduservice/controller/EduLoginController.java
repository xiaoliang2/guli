package com.athuang.eduservice.controller;

import com.athuang.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @program: guli_parent
 *
 * @description: 模拟登录
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-09 16:31
 **/
@RestController
@RequestMapping("eduservice/user")
@CrossOrigin
public class EduLoginController {

//    login
    @PostMapping("login")
    public R login()
    {
        return R.ok().data("token","admin");
    }

//    info
    @GetMapping("info")
    public R info()
    {
        return R.ok().data("roles","{admin}").data("name","admin").data("avatar","G://Chromedownloads/2c6e2fbeabdcc0a44e0bf0b4dff9819b.gif");
    }
}

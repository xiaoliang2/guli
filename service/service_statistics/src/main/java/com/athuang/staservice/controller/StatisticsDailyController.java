package com.athuang.staservice.controller;


import com.athuang.commonutils.R;
import com.athuang.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-10-04
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staservice;
//     统计某一天注册人数,生成统计数据
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day)
    {

        staservice.registerCount(day);
        return R.ok();
    }

//     图标显示，返回两部分数据，日期json数据，数量json数据
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end)
    {
        Map<String,Object> map = staservice.getShowData(type,begin,end);

        return R.ok().data(map);
    }

}


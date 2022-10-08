package com.athuang.staservice.schedule;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-10-04 17:46
 **/

import com.athuang.staservice.service.StatisticsDailyService;
import com.athuang.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-10-04 17:46
 **/
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService staService;

//     在每天凌晨一点，把前一天数据进行查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1()
    {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }


}

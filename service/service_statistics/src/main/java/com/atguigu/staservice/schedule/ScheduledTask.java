package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Davy
 */

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;

    //   0/5 * * * * ?   ->  每隔五秒执行一次这个方法
    /*@Scheduled(cron = "0/5 * * * * ?")
    public void task1(){
        System.out.println("************task1执行了.............");
    }*/

    //在每天凌晨一点，把前一天数据进行数据查询进行添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        //staService.registerCount();
    }
}

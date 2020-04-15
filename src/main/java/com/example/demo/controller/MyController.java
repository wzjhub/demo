package com.example.demo.controller;


import com.example.demo.domain.Test1;
import com.example.demo.schedual.GetSchedual;
import com.example.demo.schedual.PrintTimeJob;

import com.example.demo.service.Test1Service;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
@RequestMapping("/user")
public class MyController {




    @Autowired
    private Test1Service test1Service;

    @RequestMapping("/index")
    public List getIndex(){
        List<Test1> list = test1Service.selectAll();
        return list;
    }



    @RequestMapping("/add")
    public static void addJob() throws SchedulerException {//.setJobData(null)
        JobDetail job = JobBuilder.newJob(PrintTimeJob.class).withIdentity("job1", "group1").build();
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/2 * * * * ?")).build();//每2秒执行一次任务

        Date ft = GetSchedual.getSchedual().scheduleJob(job, trigger);//向Scheduler中注册任务
    }




    @RequestMapping("/remove")
    //传入你需要
    public void remove(@RequestParam String triggerName, @RequestParam String triggerGroupName,
                       @RequestParam String jobName, @RequestParam String jobGroupName) {
        try {
            Scheduler sched = GetSchedual.getSchedual();//还是获取那个唯一的对象（单例的嘛！）
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            //下面三个组件都需删除
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}

package com.example.demo.schedual;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest {

    public static void main(String[] args) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(PrintTimeJob.class).withIdentity("job1", "group1").build();
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/5 * * * * ?")).build();//每2秒执行一次任务

        Date ft = GetSchedual.getSchedual().scheduleJob(job, trigger);//向Scheduler中注册任务

        GetSchedual.getSchedual().start();

   }

    public static void test1() throws SchedulerException {
        //1.创建一个jobDetail，并与PrintTimeJob绑定，Job任务的name为printJob
        JobDetail jobDetail = JobBuilder.newJob(PrintTimeJob.class).withIdentity("printJob").build();

        //2.使用cron表达式，构建CronScheduleBuilder
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        //使用TriggerBuilder构建cronTrigger，并指定了Trigger的name和group
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1", "group1")
                .withSchedule(cronScheduleBuilder).build();
        //3.创建scheduler
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        //4.将job和cronTrigger加入scheduler进行管理
        scheduler.scheduleJob(jobDetail,cronTrigger);
        //5.开启调度
        scheduler.start();
    }

}

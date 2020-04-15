package com.example.demo.schedual;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintTimeJob implements Job {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String format = sdf.format(new Date());
        System.out.println(Thread.currentThread().getName()+"  任务开始>>>>>>>>>>>>>>现在时间是："+format);
        //模拟任务执行耗时5s
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"  任务结束！现在时间是："+sdf.format(new Date()));

    }
}

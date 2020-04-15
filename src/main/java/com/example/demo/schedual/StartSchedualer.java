package com.example.demo.schedual;

import org.quartz.SchedulerException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartSchedualer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        try {
            GetSchedual.getSchedual().start();//获取实例并启动
        } catch (SchedulerException e) {}
    }
}

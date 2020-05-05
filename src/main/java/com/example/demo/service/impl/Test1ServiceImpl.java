package com.example.demo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.domain.Test1;
import com.example.demo.mapper.Test1Mapper;
import com.example.demo.service.Test1Service;
import com.example.demo.snowflake.IdGeneratorSnowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Test1ServiceImpl implements Test1Service {


    @Autowired
    private IdGeneratorSnowflake idGeneratorSnowflake;

    @Autowired
    private Test1Mapper test1Mapper;

    @Override
    public List<Test1> selectAll() {
        List<Test1> list = test1Mapper.selectList(new EntityWrapper<Test1>()
                .between("id",1,10));
        return list;
    }

    @Override
    public String getIDBySnowFlake() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            threadPool.submit(() -> {


                System.out.println(idGeneratorSnowflake.snowflakeId());
            });
        }

        threadPool.shutdown();
        return "hello snowflake";
    }
}

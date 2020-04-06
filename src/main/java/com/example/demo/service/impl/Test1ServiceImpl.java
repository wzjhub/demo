package com.example.demo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.domain.Test1;
import com.example.demo.mapper.Test1Mapper;
import com.example.demo.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Test1ServiceImpl implements Test1Service {

    @Autowired
    private Test1Mapper test1Mapper;

    @Override
    public List<Test1> selectAll() {
        List<Test1> list = test1Mapper.selectList(new EntityWrapper<Test1>()
                .between("id",1,10));
        return list;
    }
}

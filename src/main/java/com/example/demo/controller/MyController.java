package com.example.demo.controller;


import com.example.demo.domain.Test1;
import com.example.demo.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}

package com.example.demo.domain;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

@Data
@TableName("test1")
public class Test1 {


  @TableId(value = "id", type = IdType.AUTO)
  private long id;



}

package com.example.demo.snowflake;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class IdGeneratorSnowflake {

    //0-31
    private long workId = 0;
    //0-31
    private long datacenterId = 0;

    private Snowflake snowflake = IdUtil.createSnowflake(workId, datacenterId);

    @PostConstruct
    public void init(){
        try {
            workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的workId: {} " ,workId);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("当前机器的workId获取失败: {}" ,e);
            workId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workId, datacenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        System.out.println(new IdGeneratorSnowflake().snowflakeId());
    }

}

package com.example.demo.nio;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {

    public static void main(String[] args) throws Exception{

        //1.获取管道
        Pipe pipe = Pipe.open();
        //2.将缓冲区的数据写入管道
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Pipe.SinkChannel sinkChannel = pipe.sink();
        byteBuffer.put("通过单向管道发送数据".getBytes());
        byteBuffer.flip();
        sinkChannel.write(byteBuffer);

        //3.读取缓冲区的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        byteBuffer.flip();
        sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(),0 , byteBuffer.limit()));

        sinkChannel.close();
        sourceChannel.close();

    }
}

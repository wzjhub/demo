package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * 使用nio 完成网络通讯的三个核心：
 * 1.通道（Channel）：负责连接
 *      java.nio.channels.Channel 接口：
 *          |--SelectableChannel
 *              |--SocketChannel
 *              |--ServerSocketChannel
 *              |--DatagramChannel
 *
 *              \--Pipo.SinkChannel
 *              \--Pipo.SourceChannel
 * 2.缓冲区(Buffer)： 负责数据的存取
 * 3.选择器(Selector)：时SelectableChannel 的多路复用器。用于监控SelectableChannel 的IO状况
 */
public class BlockingNIODemo {
    public static void main(String[] args) throws IOException {
        //server();
        //1.获取通道
        ServerSocketChannel ssc = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("D:\\file\\demo\\src\\main\\resources\\5.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ , StandardOpenOption.CREATE);

        //2.绑定连接
        ssc.bind(new InetSocketAddress(9898));
        //3.获取客户端连接的通道
        SocketChannel sc = ssc.accept();
        //4.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5.读取客户端的数据并保存到本地
        while (sc.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        //发送反馈给客户端
        buf.put("服务端接收数据结束".getBytes());
        buf.flip();
        sc.write(buf);
        //6.关闭通道
        sc.close();
        outChannel.close();
        ssc.close();
    }

    //阻塞式IO
    public static void server() throws IOException {
        //client();
        //服务端
        //1.获取通道
        ServerSocketChannel ssc = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("D:\\file\\demo\\src\\main\\resources\\4.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ , StandardOpenOption.CREATE);

        //2.绑定连接
        ssc.bind(new InetSocketAddress(9898));
        //3.获取客户端连接的通道
        SocketChannel sc = ssc.accept();
        //4.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5.读取客户端的数据并保存到本地
        while (sc.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        //6.关闭通道
        sc.close();
        outChannel.close();
        ssc.close();
    }

    public static void client() throws IOException {
        //客户端
        //1.获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\file\\demo\\src\\main\\resources\\1.jpg"), StandardOpenOption.READ);

        //2.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //3.读取本地文件，并发送到服务器
        while(inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //4.关闭通道
        inChannel.close();
        sChannel.close();
    }
}

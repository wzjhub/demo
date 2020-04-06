package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Scanner;

public class NonBlockingNIODemo {

    public static void main(String[] args) throws IOException {
        //client();
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String str = sc.next();
            byteBuffer.put((LocalDateTime.now().toString() + "\t" + str).getBytes());
            byteBuffer.flip();
            datagramChannel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 9898));
            byteBuffer.clear();
        }
        datagramChannel.close();
    }

    public static void client() throws IOException {
        //客户端
        //1.获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2.切换成非阻塞模式
        socketChannel.configureBlocking(false);

        //3.设置指定大小的缓冲区
        ByteBuffer bf = ByteBuffer.allocate(1024);

        //4.发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String str = scanner.next();
            bf.put((LocalDateTime.now().toString() + "\t " + str).getBytes());
            bf.flip();
            socketChannel.write(bf);
            bf.clear();
        }
        /*bf.put(LocalDateTime.now().toString().getBytes());
        bf.flip();
        socketChannel.write(bf);
        bf.clear();*/

        //5.关闭通道
        socketChannel.close();
    }
}

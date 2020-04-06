package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NonBlockingNIODemo2 {
    public static void main(String[] args) throws IOException {
        //server();
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(9898));
        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey sk = iterator.next();
                if(sk.isReadable()){
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    datagramChannel.receive(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array(), 0 ,byteBuffer.limit()));
                    byteBuffer.clear();
                }
            }
            iterator.remove();
        }
    }

    public static void server() throws IOException {
        //服务端
        //1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.切换成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9898));

        //4.获取选择器
        Selector selector = Selector.open();
        //5.将通道注册到选择器上,并指定监听接收事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6.轮询式的获取选择器上已经准备就绪的事件
        while (selector.select() > 0){
            //7.获取当前选择器中注册的选择键
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //8.获取准备就绪的事件
            while (it.hasNext()){
                SelectionKey sk = it.next();
                //9.判断是什么事件准备就绪
                if(sk.isAcceptable()){
                    //10.若接收就绪，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //11.客户端切换非阻塞模式
                    socketChannel.configureBlocking(false);
                    //12. 将该通道注册到选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    //13.获取当前选择器上读就绪状态的通道
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    //14.读取数据
                    ByteBuffer bf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while((len = socketChannel.read(bf)) > 0){
                        bf.flip();
                        System.out.println(new String(bf.array(),0, len));
                        bf.clear();
                    }
                }
                //取消选择键 SelectionKey
                it.remove();
            }
        }
    }
}

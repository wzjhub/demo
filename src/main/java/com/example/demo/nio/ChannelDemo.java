package com.example.demo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChannelDemo {
    public static void main(String[] args) throws  Exception{
        //channelToChannel();


        //useChannelCopyFile();
        //useChannelCopyFile2();
    }

    //通道之间的数据传输(直接缓冲区)
    public static void channelToChannel() throws IOException {

        FileChannel inChannel = FileChannel.open(Paths.get("D:\\file\\demo\\src\\main\\resources", "1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D:\\file\\demo\\src\\main\\resources", "3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ , StandardOpenOption.CREATE);

        inChannel.transferTo(0, inChannel.size(), outChannel);
        //outChannel.transferFrom(inChannel,0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    //直接缓冲区,只有byteBuffer支持
    public static void useChannelCopyFile2() throws IOException {

        FileChannel inChannel = FileChannel.open(Paths.get("D:\\file\\demo\\src\\main\\resources", "1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D:\\file\\demo\\src\\main\\resources", "3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ , StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inMappedByte = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedByte = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作
        byte[] bytes = new byte[inMappedByte.limit()];
        inMappedByte.get(bytes);
        outMappedByte.put(bytes);

        inChannel.close();
        outChannel.close();
    }


    //利用通道完成文件的复制(非直接缓冲区)
    public static void useChannelCopyFile() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fileInputStream = new FileInputStream("D:\\file\\demo\\src\\main\\resources\\1.jpg");
            fileOutputStream = new FileOutputStream("D:\\file\\demo\\src\\main\\resources\\2.jpg");

            //获取通道
            inChannel = fileInputStream.getChannel();
            outChannel = fileOutputStream.getChannel();

            //分配指定大小的缓冲区
            ByteBuffer allocate = ByteBuffer.allocate(1024);

            //将通道中的数据存入缓冲区中
            while (inChannel.read(allocate) != -1){
                allocate.flip();//切换读取数据的模式
                //将缓冲区的数据写入通道中
                outChannel.write(allocate);
                allocate.clear();//清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}

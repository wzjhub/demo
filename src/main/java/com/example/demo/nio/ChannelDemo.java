package com.example.demo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class ChannelDemo {
    public static void main(String[] args) throws  Exception{


        //charsetTest();
        //scatteringAndGathering();
        //channelToChannel();
        //useChannelCopyFile();
        //useChannelCopyFile2();
    }


    public static void charsetTest() throws CharacterCodingException {
        //字符集Charset
        //SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        //Set<Map.Entry<String, Charset>> entries = stringCharsetSortedMap.entrySet();
        //entries.forEach(System.out::println);
        Charset cs1 = Charset.forName("GBK");
        //获取编码器
        CharsetEncoder charsetEncoder = cs1.newEncoder();
        //获取解码器
        CharsetDecoder charsetDecoder = cs1.newDecoder();
        CharBuffer cbf = CharBuffer.allocate(1024);
        cbf.put("这是一个串");
        cbf.flip();
        //编码
        ByteBuffer encode = charsetEncoder.encode(cbf);
        for (int i = 0; i < 10 ; i++) {
            System.out.println(encode.get());
        }

        //解码
        encode.flip();
        CharBuffer decode = charsetDecoder.decode(encode);
        System.out.println(decode.toString());

        System.out.println("===================================");
        Charset charset = Charset.forName("UTF-8");
        encode.flip();
        CharBuffer decode1 = charset.decode(encode);
        System.out.println(decode1.toString());
    }

    //分散和聚集
    public static void scatteringAndGathering() throws IOException {
        //分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
        //聚集写入（Gathering Reads）

        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\file\\demo\\src\\main\\resources\\mysql.txt","rw");
        //获取通道
        FileChannel channel = randomAccessFile.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        //分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel.read(bufs);
        for (ByteBuffer bf: bufs) {
            bf.flip();
        }
        System.out.println(new String(bufs[0].array(),0, bufs[0].limit()));
        System.out.println("===========================");
        System.out.println(new String(bufs[1].array(),0, bufs[1].limit()));

        RandomAccessFile randomAccessFile2 = new RandomAccessFile("D:\\file\\demo\\src\\main\\resources\\mysql-1.txt","rw");
        FileChannel channel2 = randomAccessFile2.getChannel();
        channel2.write(bufs);
        channel.close();
        channel2.close();
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

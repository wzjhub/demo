package com.example.demo.work;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    public static void main(String[] args) {
        //由数组结构组成的有界阻塞队列
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(3);

        System.out.println(bq.add("a"));
        System.out.println(bq.add("a"));
        System.out.println(bq.add("a"));

        System.out.println(bq.element());

        System.out.println(bq.remove());
        System.out.println(bq.remove());
        System.out.println(bq.remove());
    }
}

package com.example.demo.work;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " \t 上完自习，离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + " \t 班长上完自习，最后离开教室");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

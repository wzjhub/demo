package com.example.demo.work;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ABADemo {
    //ABA问题的解决  AtomicStampeReference
    static AtomicReference<Integer> ar = new AtomicReference<>(100);

    public static void main(String[] args) {
        new Thread(()->{
            ar.compareAndSet(100,101);
            ar.compareAndSet(101,100);
        },"t1").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(ar.compareAndSet(100,2019) + "\t" + ar.get());
        },"t2").start();
    }
}

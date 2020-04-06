package com.example.demo.work;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable{
    String lockA;
    String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有： " + lockA + " \t 尝试获得： " +lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){

            System.out.println(Thread.currentThread().getName()+"\t 自己持有： " + lockB + " \t 尝试获得： " +lockA);
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA,lockB),"线程A").start();
        new Thread(new HoldLockThread(lockB,lockA),"线程B").start();
    }
}

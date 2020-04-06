package com.example.demo.work;



import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile/atomic/BlockQueue/线程交互/原子引用
 *  线程    操作     资源类
 */
class MyResource{
    private  volatile boolean FLAG = true; //默认开启，进行生产+消费
    private AtomicInteger atomicInteger=new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public  void myProd() throws Exception{
        String data = null;
        boolean retValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t FLAG=false,生产动作结束");
    }

    public void myConsumer() throws Exception {
        //while是为了防止虚假唤醒
        String result = null;
        while (FLAG){
           result = blockingQueue.poll(2L, TimeUnit.SECONDS);
           if(result == null || result.equalsIgnoreCase("")){
               FLAG = false;
               System.out.println(Thread.currentThread().getName()+"\t 超过两秒没有取到蛋糕，消费退出");
               System.out.println();
               System.out.println();
               return;
           }
           System.out.println(Thread.currentThread().getName()+"\t 消费队列蛋糕"+result+"成功");
        }
    }

    public  void stop() throws Exception{
        this.FLAG = false;
    }

}

public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
                System.out.println();
                System.out.println();
            try {
                myResource.myConsumer();
                System.out.println();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        myResource.stop();
        System.out.println("5秒时间到，main线程叫停，活动结束");
    }
}

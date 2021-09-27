package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/19 09:19
 * Description: Semaphore 信号量/信号灯
 * 用于限流
 * 例子：车道与收费站的关系。
 */
public class T11_TestSemaphore {
    public static void main(String[] args) {
//        Semaphore s = new Semaphore(2);
        Semaphore s = new Semaphore(2,true);

        new Thread(()->{
            try {
                s.acquire();
                System.out.println("T1 running...");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("T1 stop...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }
        }).start();

        new Thread(()->{
            try {
                s.acquire();
                System.out.println("T2 running...");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("T2 stop...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }
        }).start();

        new Thread(()->{
            try {
                s.acquire();
                System.out.println("T3 running...");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("T3 stop...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }
        }).start();
    }

    /**
     * 信号量有个坑，如果初始化个数为0，直接使用acquire()或tryAcquire()方法是不行，因为此时没有许可证。
     * 必须要先释放许可证release(),此时信号量许可证加1，在调用前面的方法就可以了。
     * 使用场景，不知道要多少许可证的情况下，就必须这么做。
     * 之所以有这个想法，是因为看了Eureka的源码，发现它就是使用new Semaphore(0)。
     */
    public static void test(){
        Semaphore s = new Semaphore(0);
        s.release();
        boolean b = s.tryAcquire(1);
        System.out.println(b);
        int i = s.availablePermits();
        System.out.println(i);
    }
}

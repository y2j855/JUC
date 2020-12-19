package com.tony.juc.c_013_CAS_Lock;

import java.sql.Time;
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
                System.out.println("T1 running...");
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
                System.out.println("T2 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }
        }).start();
    }
}

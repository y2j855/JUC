package com.tony.juc.c_020_interview_A1B2C3;

import java.util.concurrent.CountDownLatch;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 19:26
 * Description: CountDownLatch替代boolean
 */
public class T07_sync_wait_notify {
//    private static volatile boolean t2Started = false;
    //利用countdownlatch 替代 boolean
    private static CountDownLatch latch = new CountDownLatch(1);

    private static Object o = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o) {
                for (int i = 1; i <= 26; i++) {
                    System.out.println(i);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o) {
                for (int i = 65; i < 91; i++) {
                    char c = (char) (i);
                    System.out.println(c);
                    latch.countDown();
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t2").start();
    }
}

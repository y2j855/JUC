package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/19 09:30
 * Description: LockSupport
 * park() 锁定线程，停止运行，阻塞。
 * unpark() 解除锁定，继续运行
 * unpark() 可以在park之前运行
 *
 * 以前让一个线程阻塞需要用到wait，await，并且必须获取这把锁，然后唤醒notify，notifyAll也要获取锁
 * 而且唤醒某个指定的线程比较麻烦。
 *
 * LockSupport不需要这么繁琐，只要把线程对象传给它就可以了。所以更灵活
 * 并且唤醒可以在阻塞之前运行。
 *
 */
public class T13_TestLockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5) {
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        LockSupport.unpark(t);
        /*try {
            TimeUnit.SECONDS.sleep(8);
            LockSupport.unpark(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }
}

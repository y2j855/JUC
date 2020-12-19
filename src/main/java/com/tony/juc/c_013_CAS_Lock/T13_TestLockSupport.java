package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/19 09:30
 * Description: LockSupport
 * park() 锁定线程，停止运行
 * unpark() 解除锁定，继续运行
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

        try {
            TimeUnit.SECONDS.sleep(8);
            LockSupport.unpark(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

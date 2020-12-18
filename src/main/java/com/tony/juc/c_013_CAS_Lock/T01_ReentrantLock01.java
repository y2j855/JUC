package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 11:14
 * Description: ReentrantLock可重入锁
 * 本例中由于m1锁定this，只有m1执行完毕的时候，m2才能执行
 * 因为m1，m2是两个不同的线程。
 * 可重入锁是指在同一个线程下，m1调用了m2，m2执行完m1又会重新获取这把锁
 */
public class T01_ReentrantLock01 {
    synchronized void m1(){
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
            //ReentrantLock可重入锁
            if(i==2){
                m2();
            }
        }
    }

    private synchronized void m2() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m2 ..." + i);
        }
    }

    public static void main(String[] args) {
        T01_ReentrantLock01 rl = new T01_ReentrantLock01();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        new Thread(rl::m2).start();
    }
}

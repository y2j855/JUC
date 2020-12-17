package com.tony.juc.c_007_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 11:29
 * Description: synchronized获得的锁是可重入的
 * 一个同步方法可以调用另一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁
 *
 */
public class T {
    synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + " m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    private synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2");
    }

    public static void main(String[] args) {
        new T().m1();
    }
}

package com.tony.juc.c_007_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 11:44
 * Description: synchronized一个线程锁可重入
 * 继承中的场景
 */
public class Father {
    synchronized void m(){
        System.out.println(Thread.currentThread().getName() + " m start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m end");
    }
}

class Son extends Father{
    @Override
    synchronized void m() {
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }
}

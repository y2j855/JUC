package com.tony.juc.c_009_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 16:09
 * Description:
 * volatile 引用类型(包括数组)只能保证引用本身的可见性，不能保证内部字段的可见性
 */
public class T02_VolatileReference1 {
    /*volatile*/ boolean running = true;

    volatile static T02_VolatileReference1 T = new T02_VolatileReference1();

    void m(){
        System.out.println("m start");
        while (running) {
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        new Thread(T::m,"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        T.running = false;

    }

}

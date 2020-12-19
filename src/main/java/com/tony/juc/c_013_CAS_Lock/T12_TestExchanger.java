package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.Exchanger;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/19 09:26
 * Description: Exchanger 两个线程之间的数据交换
 */
public class T12_TestExchanger {
    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            String s = "T1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);
        },"t1").start();

        new Thread(()->{
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);
        },"t2").start();
    }
}

package com.tony.juc.c_000;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/16 20:46
 * Description: 什么是线程
 */
public class T01_WhatIsThread {
    private static class T1 extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1");
            }
        }
    }

    public static void main(String[] args) {
        //run方法不会开启一个新的线程，顺序执行。
//        new T1().run();
        //start方法开启一个新的线程，并行执行。
        new T1().start();
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main");
        }
    }
}

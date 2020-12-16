package com.tony.juc.c_000;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/16 20:56
 * Description: 创建线程的方式
 * 启动线程的三种方式
 * 1.Thread
 * 2.Runnable
 * 3.Executors.newCachedThread 通过线程池启动
 */
public class T02_HowToCreateThread {
    private static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    private static class MyRun implements Runnable{

        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(()->{
            System.out.println("Hello Lambda!");
        }).start();
    }
}

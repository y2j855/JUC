package com.tony.juc.c_000;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/16 21:26
 * Description: 线程状态
 * 1.NEW        新建
 * 2.RUNNABLE   运行中
 * 3.TERMINATED 停止
 */
public class T04_ThreadState {
    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println(this.getState());
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new MyThread();
        System.out.println(t.getState());
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getState());
    }
}

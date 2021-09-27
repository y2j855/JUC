package com.tony.juc.c_000.threadstate;

/**
 * @author: Tony.Chen
 * Create Time : 2021/4/10 10:44
 * Description: sleep(long)时线程状态：TIMED_WAITING
 */
public class Test1 {
    public static void main(String[] args) {
        Thread1 t1 = new Thread1();
        Thread2 t2 = new Thread2();
        t1.setT2(t2);
        t1.start();
        t2.start();
    }
}

class Thread1 extends Thread{
    private Thread2 t2;

    public void setT2(Thread2 t2) {
        this.t2 = t2;
    }

    @Override
    public void run() {
        System.out.println("进入t1线程");
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("t1的状态: " + getState());
                System.out.println("t2的状态 " + t2.getState());
                System.out.println();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Thread2 extends Thread{
    @Override
    public void run() {
        System.out.println("进入t2线程，马上进入睡眠");
        try {
            Thread.sleep(5000);
            System.out.println("111");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t2睡眠结束");
    }
}

package com.tony.juc.c_020_interview_A1B2C3;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/27 10:54
 * Description:
 * 程序存在的问题，只能是1A2B3C...26Z。
 * 如果想先输出A1B2...？首先要变换t1,t2中的notify,wait的位置
 * 并且让t2先开始，t1再开始。不是很灵活
 *
 * 如果t1,t2两个方法都是先notify再wait
 * 就需要在for之后notify，否则线程就会始终阻塞着。
 */
public class T06_sync_wait_notify {
    static Thread t1 = null;
    static Thread t2 = null;
    static final Object o = new Object();

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            synchronized (o) {
                for (int i = 1; i <= 26; i++) {
                    System.out.println(i);
                    try {
                        o.wait();
                        o.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1");

        t2 = new Thread(() -> {
            synchronized (o) {
                for (int i = 65; i < 91; i++) {
                    char c = (char) (i);
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}

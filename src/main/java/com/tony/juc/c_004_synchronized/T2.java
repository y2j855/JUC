package com.tony.juc.c_004_synchronized;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 10:52
 * Description: synchronized
 * 解决了T1的问题
 * synchronized的特性
 * 1.原子性
 * 2.可见性，所以不用加volatile
 */
public class T2 implements Runnable {
    private int count = 100;

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName()+" count = " +count);
    }

    public static void main(String[] args) {
        T2 t = new T2();
        for (int i = 0; i < 5; i++) {
            new Thread(t,"Thread" + i).start();
        }
    }
}

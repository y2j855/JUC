package com.tony.juc.c_004_synchronized;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 10:49
 * Description: synchronized
 * 分析程序的问题
 * 1.如果不加同步，导致count输出结构线程之间互相影响。
 */
public class T1 implements Runnable{
    private int count = 100;

    @Override
    public void run() {
        count--;
        System.out.println(Thread.currentThread().getName()+" count = " +count);
    }

    public static void main(String[] args) {
        T1 t = new T1();
        for (int i = 0; i < 100; i++) {
            new Thread(t,"THREAD " + i).start();
        }
    }
}

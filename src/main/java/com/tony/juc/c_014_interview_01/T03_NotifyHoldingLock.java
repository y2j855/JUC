package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 12:30
 * Description:
 * 使用waite和notify做到，wait会释放锁，而notify不会释放锁。
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 *
 * 程序的问题:notify不会释放锁，所以t1必须释放锁才能让t2运行
 */
public class T03_NotifyHoldingLock {
    private List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        T03_NotifyHoldingLock t = new T03_NotifyHoldingLock();
        final Object lock = new Object();
        new Thread(()->{
            synchronized (lock){
                System.out.println("t2启动");
                if (t.size()!=5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
            }

        },"t2").start();

        new Thread(()->{
            synchronized (lock){
                System.out.println("t1启动");
                for (int i = 0; i < 10; i++) {
                    t.add("add " + i);
                    System.out.println("add " + i);
                    if(t.size()==5){
                        lock.notify();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

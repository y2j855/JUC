package com.tony.juc.c_014_interview_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/20 14:53
 * Description:
 * 解决notify不能释放锁问题
 * wait():释放锁，如果还要继续执行必须再次拿到锁
 * notify():不释放锁。
 * 缺点：整个通信比较繁琐，一会通知，一会等待。
 */
public class T04_NotifyFreeLock {
    private List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        T04_NotifyFreeLock t = new T04_NotifyFreeLock();
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
                //通知t1继续执行
                lock.notify();
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
                        //释放锁，让t2执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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

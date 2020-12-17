package com.tony.juc.c_013_CAS_ABA;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 20:59
 * Description: CAS重现ABA问题
 * ABA问题描述
 * 一个线程把数据A变为了B，然后又重新变成了A。
 * 此时另外一个线程读取的时候，发现A没有变化，就误以为是原来的那个A。
 * 这就是有名的ABA问题。
 */
public class AtomicTest {
    private static AtomicInteger index = new AtomicInteger(10);

    public static void main(String[] args) {
        new Thread(()->{
            index.compareAndSet(10,11);
            index.compareAndSet(11,10);
            System.out.println(Thread.currentThread().getName() + ": 10->11->10");
        },"张三").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = index.compareAndSet(10,12);
                System.out.println(Thread.currentThread().getName()
                + ":index是预期的10吗?" + isSuccess
                +" 设置的新值是:" + index.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"李四").start();
    }
}

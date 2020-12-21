package com.tony.juc.c_016_RefTypeAndThreadLocal;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/21 20:11
 * Description: ThreadLocal线程局部变量
 * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
 * 比如spring的声明式事务，根据配置文件配置多个方法在一个事务中，
 * 保证同一个线程下多个方法使用的是同一个conncetion，这就是使用了ThreadLocal的概念。
 *
 * 通过查看源码，发现threadlocal把值放到了当前线程的map中，这样每个线程都有自己的值。
 * 所以互相不会受影响。
 *
 * ThreadLocal还使用了弱引用
 * 保证了tl被GC回收，map中的key就被回收
 * 但还是存在一个问题，如果key=null，那么通过key就拿不到value的值，但value的值还存在
 * 这样还是会导致内存泄露的问题。
 * 解决办法，就是当用完后一定要remove。
 */
public class ThreadLocal2 {
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(tl.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
    static class Person{
        String name = "zhangsan";
    }

}

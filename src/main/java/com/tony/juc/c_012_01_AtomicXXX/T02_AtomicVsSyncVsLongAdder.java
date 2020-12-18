package com.tony.juc.c_012_01_AtomicXXX;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 09:07
 * Description:
 * AtomicLong synchronized LongAdder 性能测试
 * 发现LongAdder速度快很多，原因？
 * 原因LongAdder是采用了分段锁的概念，它将线程存放到数组中，进行了拆分。
 * 这样很多线程变成了并行操作，最后再汇总算出结果。所以性能高，但使用场景是线程数比较多的情况。
 * 如果线程数比较少LongAdder不见得性能高于AtomicLong和synchronized
 */
public class T02_AtomicVsSyncVsLongAdder {
    static long count1 = 0L;
    static AtomicLong count2 = new AtomicLong(0);
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) {
        Thread[] threads = new Thread[1000];
        syncRunTime(threads);
        atomicRunTime(threads);
        longAdderRunTime(threads);

    }

    /**
     * LongAdder运行时间
     * @param threads
     */
    private static void longAdderRunTime(Thread[] threads) {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++) {
                    count3.increment();
                }
            });
        }
        long start = System.currentTimeMillis();

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Atomic: " + count3.longValue() + " time " + (end-start));
    }

    /**
     * AtomicLong运行时间
     * @param threads
     */
    private static void atomicRunTime(Thread[] threads) {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++) {
                   count2.incrementAndGet();
                }
            });
        }
        long start = System.currentTimeMillis();

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Atomic: " + count2.get() + " time " + (end-start));
    }

    /**
     * synchronized执行时间
     * @param threads
     */
    private static void syncRunTime(Thread[] threads) {
        Object lock = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++) {
                    synchronized (lock) {
                        count1++;
                    }
                }
            });
        }
        long start = System.currentTimeMillis();

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Sync: " + count1 + " time " + (end-start));
    }

}

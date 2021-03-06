package com.tony.juc.c_017_FromHashtableToCHM;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 10:11
 * Description: CHM性能测试
 * 通过测试发现
 * chm写数据性能不如hashtable和synchashmap
 * 但是读数据的性能远远大于前两种。
 * 所以chm适用于写很少但读很多的场景。
 * 数据结构：1.7 数组+链表 1.8 数组+链表+红黑树
 */
public class T04_TestConcurrentHashMap {
    static Map<UUID, UUID> map = new ConcurrentHashMap<>();

    static int count = Constants.COUNT;
    static final int THREAD_COUNT = Constants.THREAD_COUNT;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread {
        int start;
        int gap = count / THREAD_COUNT;

        public MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < start + gap; i++) {
                map.put(keys[i], values[i]);
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(i * (count / THREAD_COUNT));
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("写数据的时间=" + (end - start));
        System.out.println(map.size());

        //---------------------------------

        start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000000; j++) {
                    map.get(keys[10]);
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.println("读数据的时间=" + (end - start));

    }
}

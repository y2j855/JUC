package com.tony.juc.c_019_MapAndSet;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 14:45
 * Description: 写时复制容器  copy on write
 * 多线程环境下，写时效率低，读时效果高
 * 适合多读少写的环境 与ConcurrentHashMap使用场景类似
 */
public class T02_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        Thread[] threads = new Thread[100];

        writeTime(list, threads);
        System.out.println(list.size());
        readTime(list, threads);

    }

    private static void writeTime(List<String> list, Thread[] threads) {
        Random r = new Random();
        for (int i = 0; i < threads.length; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        list.add("a" + r.nextInt(100000));
                    }
                }
            };
            threads[i] = new Thread(task);
        }

        long start = System.currentTimeMillis();
        Arrays.asList(threads).forEach(t->t.start());
        Arrays.asList(threads).forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("写数据的时间="+(end - start));
    }

    private static void readTime(List<String> list, Thread[] threads) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            new Thread(()->{
                for (int j = 0; j < 100000; j++) {
                    list.get(10);
                }
            }).start();
        }
        long end = System.currentTimeMillis();
        System.out.println("读数据的时间="+(end - start));
    }


}

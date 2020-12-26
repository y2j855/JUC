package com.tony.juc.c_019_MapAndSet;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/26 11:15
 * Description:
 * http://blog.csdn.net/sunxianghuang/article/details/52221913
 * 阅读concurrentskiplistmap
 * 测试其他Map分支的并发容器性能
 * ConcurrentSkipListMap
 * 具体看c_017_FromHashtableToCHM
 */
public class T01_ConcurrentMap {
    public static void main(String[] args) {

        Map<String,String> map = new ConcurrentHashMap<>();
        //Map<String, String> map = new ConcurrentSkipListMap<>(); //高并发并且排序

        //Map<String, String> map = new Hashtable<>();
        //Map<String, String> map = new HashMap<>(); //Collections.synchronizedXXX
        //TreeMap

        Random r = new Random();
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    map.put("a" + r.nextInt(100000),
                            "a" + r.nextInt(100000));
                }
                latch.countDown();
            });
        }

        Arrays.asList(threads).forEach(t->t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(map.size());


    }
}

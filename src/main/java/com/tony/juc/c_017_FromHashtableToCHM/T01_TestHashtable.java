package com.tony.juc.c_017_FromHashtableToCHM;

import java.util.Hashtable;
import java.util.UUID;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/25 21:00
 * Description: 测试hashtable性能
 * 多线程测试
 */
public class T01_TestHashtable {
    /**
     * 1.定义常量
     * 2.创建key value UUID数组
     * 3.初始化数组内容
     * 4.自定义线程对象，让每个线程往hashtable中平均放数据，读数据
     * 5.主函数计算读写时间
     */

    static Hashtable<UUID, UUID> table = new Hashtable<>();

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
            for (int i = start; i < gap + start; i++) {
                table.put(keys[i], values[i]);
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(i * (count / THREAD_COUNT));
        }
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("写数据的时间=" + (end - start));
        System.out.println(table.size());
        //----------------------------------

        start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 10000000; j++) {
                    table.get(keys[10]);
                }
            });
        }

        for (Thread t : threads){
            t.start();
        }

        for (Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();
        System.out.println("读数据时间=" + (end - start));
    }
}

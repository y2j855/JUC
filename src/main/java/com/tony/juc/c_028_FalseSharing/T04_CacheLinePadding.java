package com.tony.juc.c_028_FalseSharing;

/**
 * @author: Tony.Chen
 * Create Time : 2020/10/22 13:00
 * Description:
 * 缓存行性能测试
 * 一块数据在不同的缓存行中，多个线程对其进行操作。
 * 性能更高
 */
public class T04_CacheLinePadding {

    public static volatile long[] arr = new long[16];

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < 10000_0000L; i++) {
                arr[0] = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < 10000_0000L; i++) {
                arr[8] = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start) / 100_0000);
    }
}

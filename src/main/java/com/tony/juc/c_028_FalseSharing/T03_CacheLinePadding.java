package com.tony.juc.c_028_FalseSharing;

/**
 * @author: Tony.Chen
 * Create Time : 2020/10/22 12:59
 * Description:
 * 模拟cpu缓存一致性/缓存锁问题
 * 缓存行性能测试
 * 不同的线程对同一个缓存行数据进行操作
 * 性能低
 */
public class T03_CacheLinePadding {

    public static volatile long[] arr = new long[2];

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(()->{
            for (long i = 0; i < 10000_0000L; i++) {
                arr[0] = i;
            }
        });

        Thread t2 = new Thread(()->{
            for (long i = 0; i < 10000_0000L; i++) {
                arr[1] = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start)/100_0000);
    }
}

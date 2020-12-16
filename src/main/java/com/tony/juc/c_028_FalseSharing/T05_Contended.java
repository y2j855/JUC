package com.tony.juc.c_028_FalseSharing;

import jdk.internal.vm.annotation.Contended;

/**
 * @author: Tony.Chen
 * Create Time : 2020/10/22 13:12
 * Description:
 * jdk对缓存行的优化
 * JDK7使用long padding方式提高效率
 * JDK8使用Contended注解，对缓存行进行优化，提高效率。需要加上:JVM -XX:-RestrictContended
 */
public class T05_Contended {
    @Contended
    volatile long x;
    @Contended
    volatile long y;

    public static void main(String[] args) throws InterruptedException {
        T05_Contended t = new T05_Contended();
        Thread t1 = new Thread(()->{
            for (long i = 0; i <1_0000_0000L ; i++) {
                t.x = i;
            } 
        });

        Thread t2 = new Thread(()->{
            for (long j = 0; j < 1_0000_0000L; j++) {
                t.y = j;
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

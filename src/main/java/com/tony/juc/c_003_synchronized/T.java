package com.tony.juc.c_003_synchronized;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 10:17
 * Description: synchronized
 * T.class = static method
 */
public class T {
    private static int count = 10;

    public synchronized static void m(){
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void n(){
        synchronized (T.class){
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}

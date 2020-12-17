package com.tony.juc.c_001_synchronized;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 09:52
 * Description: synchronized
 * 对某个对象加锁
 */
public class T {
    private int count = 0;
    private Object o = new Object();

    public void m() {
        //任何线程要执行下面的代码，必须先拿到o的锁
        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}

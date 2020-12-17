package com.tony.juc.c_002_synchronized;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 10:06
 * Description: synchronized
 * 对某个对象加锁,使用this
 *
 */
public class T {
    private int count = 10;

    public void m(){
        //任何线程要执行下面的代码，必须先拿到this的锁，也就是对象本身
        synchronized (this){
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    /**
     * synchronized(this)等同于方法上加关键字
     */
    public synchronized void n(){
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}

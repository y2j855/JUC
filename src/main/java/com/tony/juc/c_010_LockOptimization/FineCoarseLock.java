package com.tony.juc.c_010_LockOptimization;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/17 16:35
 * Description: synchronized锁的优化
 * 锁的细化 只在需要的业务逻辑上加锁，不需要真个方法加锁。
 * 锁的粗化 如果细化锁太多，比如一个方法很多地方都加了细化锁，导致频繁的抢占锁，不如整个方法上加锁。
 */
public class FineCoarseLock {
    int count = 0;

    synchronized void m(){
        //do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //业务逻辑需要加锁的地方，此时不应该这个方法加锁
        count++;

        //do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void m2(){
        //do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //采用细粒度的锁，可以使线程争用时间变短，从而提高效率。
        synchronized (this){
            count++;
        }

        //do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.tony.juc.c_013_CAS_Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Tony.Chen
 * Create Time : 2020/12/18 11:26
 * Description: ReentrantLock替代Synchronized
 * 使用ReentrantLock可以完成sync同样的功能
 * 需要注意的是，必须要手动释放锁！！！
 * 使用sync锁定的话如果遇到异常，jvm自动释放锁。
 * 但是lock必须手动释放锁，因此经常在finally中进行锁的释放操作。
 */
public class T02_ReentrantLock02 {
    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();    //synchronize(this)
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        lock.lock();
        try {
            System.out.println("m2...");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        T02_ReentrantLock02 rl = new T02_ReentrantLock02();
        new Thread(rl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::m2).start();
    }
}
